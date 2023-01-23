package com.hou27.basicboard.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class PaginationService {
  private static final int BAR_LENGTH = 5;

  public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
    int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
    int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);

    // boxed로 Integer 클래스로 변환하여 List<Integer> 로 담아준다.
    return IntStream.range(startNumber, endNumber).boxed().toList();
  }

  public int currentBarLength() {
    return BAR_LENGTH;
  }

}
