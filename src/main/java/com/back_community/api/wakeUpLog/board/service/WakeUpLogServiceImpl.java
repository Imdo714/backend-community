package com.back_community.api.wakeUpLog.board.service;

import com.back_community.api.common.embedded.board.Board;
import com.back_community.api.common.page.PageInfo;
import com.back_community.api.common.util.S3Upload;
import com.back_community.api.user.domain.entity.User;
import com.back_community.api.wakeUpLog.board.domain.dto.request.*;
import com.back_community.api.wakeUpLog.board.domain.dto.response.CreateWakeUpResponse;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WakeUpLogDetailResponse;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WakeUpLogListResponse;
import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import com.back_community.api.wakeUpLog.dao.WakeUpLogDao;
import com.back_community.global.exception.handleException.MismatchException;
import com.back_community.global.exception.handleException.S3UploadFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WakeUpLogServiceImpl implements WakeUpLogService {

    private final WakeUpLogDao wakeUpLogDao;
    private final S3Upload s3Upload;

    @Override
    @Transactional
    public CreateWakeUpResponse createWakeUpLog(CreateWakeUpLogDto createWakeUpLogDto, MultipartFile image, Long userId) {
        User user = wakeUpLogDao.getUserLock(userId);
//        wakeUpLogDao.validateNotWakeUpLogToday(userId); // 오늘 작성했는지 안했는지 확인

        String imageUpload = null;
        if(image != null){
            imageUpload = s3ImageUpload(image);
        }

        Board board = Board.builderBoard(createWakeUpLogDto.getTitle(), createWakeUpLogDto.getContent());
        WakeUpLog wakeUpLog = WakeUpLog.builderWakeUpLog(user, board, imageUpload);

        WakeUpLog saved = wakeUpLogDao.saveWakeUpLog(wakeUpLog);
        yesterdayWakeUpLogStreak(userId, user);

        return CreateWakeUpResponse.createWakeUpSuccess(saved.getWakeUpId(), user.getWakeUpStreak());
    }

    private String s3ImageUpload(MultipartFile image) {
        try{
           return s3Upload.upload(image, "backend/wake-up");
        } catch (IOException e){
            throw new S3UploadFailException("이미지 업로드 실패");
        }
    }

    @Override
    public WakeUpLogListResponse getWakeUpLogList(int page, int size) {
        Page<WakeUpListDto> wakeUpLogList2 = wakeUpLogDao.getWakeUpLogList(page, size);

        List<WakeUpListDto> list = wakeUpLogList2.getContent();
        PageInfo pageInfo = PageInfo.pageBuilder(wakeUpLogList2);

        return WakeUpLogListResponse.builder()
                .wakeUpLists(list)
                .pageable(pageInfo)
                .build();
    }

    @Override
    public WakeUpLogDetailResponse wakeUpLogDetail(Long logId) {
        WakeUpLog wakeUpLogDetail = wakeUpLogDao.getWakeUpLog(logId);
        int countWakeUpLogLikes = wakeUpLogDao.getCountWakeUpLogLikes(logId);

        return WakeUpLogDetailResponse.of(wakeUpLogDetail, countWakeUpLogLikes);
    }

    @Override
    @Transactional
    public WakeUpLogDetailResponse wakeUpLogUpdate(Long logId, Long userId, UpdateWakeUpLogDto updateWakeUpLogDto) {
        WakeUpLog wakeUpLog = validateWakeUpUserIsOwner(userId, logId);
        int countWakeUpLogLikes = wakeUpLogDao.getCountWakeUpLogLikes(logId);

        wakeUpLog.getBoard().updateBoard(updateWakeUpLogDto);
        return WakeUpLogDetailResponse.of(wakeUpLog, countWakeUpLogLikes);
    }

    @Override
    @Transactional
    public void wakeUpLogDelete(Long logId, Long userId) {
        WakeUpLog wakeUpLog = validateWakeUpUserIsOwner(userId, logId);
        wakeUpLogDao.deleteWakeUpLog(wakeUpLog.getWakeUpId());
    }

    private void yesterdayWakeUpLogStreak(Long userId, User user) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime startOfYesterday = yesterday.atStartOfDay();
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();

        user.wakeUpStreakUpdate(wakeUpLogDao.yesterdayByUserIdAndDate(userId, startOfYesterday, startOfToday));
    }

    private WakeUpLog validateWakeUpUserIsOwner(Long userId, Long logId) {
        WakeUpLog wakeUpLog = wakeUpLogDao.getWakeUpLog(logId);

        if (!userId.equals(wakeUpLog.getUser().getUserId())) {
            throw new MismatchException("작성자가 다릅니다!");
        }

        return wakeUpLog;
    }
}
