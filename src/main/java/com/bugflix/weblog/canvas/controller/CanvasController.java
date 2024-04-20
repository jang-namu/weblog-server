package com.bugflix.weblog.canvas.controller;

import com.bugflix.weblog.canvas.dto.CanvasRequest;
import com.bugflix.weblog.canvas.dto.CanvasResponse;
import com.bugflix.weblog.canvas.dto.CanvasSearchRequest;
import com.bugflix.weblog.canvas.service.CanvasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CanvasController {

    private final CanvasService canvasService;

    /**
     * 사용자의 캔버스(지식나무) 메타데이터(title, URL, 소유주 정보)를 저장합니다.
     *
     * @param request 제목(title)은 NotNull, key는 공백 또는 null일 수 없습니다.(key: 저장 URL)
     * @param userDetails 저장을 요청한 user에 대한 정보;
     *                    이를통해 요청자의 캔버스로 저장됩니다.
     * @return 성공 코드( 사용자의 요청을 성공적으로 처리한 경우 )
     */
    @PostMapping("/v1/canvases")
    public ResponseEntity<?> saveCanvas(@RequestBody CanvasRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        canvasService.save(request, userDetails);
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자가 요청한 캔버스(지식나무)를 업데이트합니다.
     *
     * @param canvasId 사용자가 업데이트 요청한 캔버스의 Id ( Primary Key ).
     * @param request 제목(title)은 NotNull, key는 공백 또는 null일 수 없습니다.(key: 저장 URL)
     * @param userDetails 업데이트를 요청한 user에 대한 정보;
     *                    요청자가 캔버스의 소유자인지 확인합니다.
     * @return 성공 코드( 사용자의 요청을 성공적으로 처리한 경우 )
     */
    @PutMapping("/v1/canvases/{canvasId}")
    public ResponseEntity<?> updateCanvas(@PathVariable long canvasId,
                                          @RequestBody CanvasRequest request,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        canvasService.update(canvasId, request, userDetails);
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자가 요청한 캔버스(지식나무)를 삭제합니다.
     *
     * @param canvasId 사용자가 업데이트 요청한 캔버스의 Id ( Primary Key ).
     * @param userDetails 삭제를 요청한 user에 대한 정보;
     *                    요청자가 캔버스의 소유자인지 확인합니다.
     * @return 성공 코드( 사용자의 요청을 성공적으로 처리한 경우 )
     */
    @DeleteMapping("/v1/canvases/{canvasId}")
    public ResponseEntity<?> deleteCanvas(@PathVariable long canvasId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        canvasService.delete(canvasId, userDetails);
        return ResponseEntity.ok().build();
    }

    /**
     * (페이징) 최근 생성된 캔버스 목록을 조회합니다.
     *
     * @param offset 조회 시작 인덱스
     * @param limit 한 번에 조회할 갯수
     * @return 생성 시간을 기준으로 정렬하여 최근 (offset)부터 (limit)개의 캔버스 메타데이터 List
     */
    @GetMapping("/v1/canvases")
    public ResponseEntity<List<CanvasResponse>> getRecentCanvases(@RequestParam Integer offset, @RequestParam Integer limit) {
        return ResponseEntity.ok().body(canvasService.getRecentCanvases(offset, limit));
    }

    /**
     * (페이징) 사용자가 소유한 캔버스 목록을 조회합니다.
     *
     * @param offset 조회 시작 인덱스
     * @param limit 한 번에 조회할 갯수
     * @param userDetails 조회를 요청한 user에 대한 정보;
     *                    요청자 정보로 자신의 캔버스를 조회합니다.
     * @return (offset)부터 (limit)개의 사용자 소유의 캔버스 메타데이터 List
     */
    @GetMapping("/v1/canvases/mine")
    public ResponseEntity<List<CanvasResponse>> getMyCanvases(@RequestParam Integer offset, @RequestParam Integer limit,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(canvasService.getMyCanvases(offset, limit, userDetails));
    }

    /**
     * (페이징/검색) 제목, 작성자를 기준으로 키워드에 일치하는 캔버스 목록을 조회합니다.
     *
     * @param request 검색 키워드(문자열), 검색 타입, 조회 시작 인덱스, 한 번에 조회할 갯수를 지정합니다.
     * @return (offset)부터 (limit)개의 검색 키워드(query)와 일치하는 캔버스 메타데이터
     */
    @GetMapping("/v1/search/canvases")
    public ResponseEntity<List<CanvasResponse>> searchCanvases(@ModelAttribute CanvasSearchRequest request) {
        return ResponseEntity.ok().body(canvasService.search(request));
    }
}
