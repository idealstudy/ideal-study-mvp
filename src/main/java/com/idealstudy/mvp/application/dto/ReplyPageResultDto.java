package com.idealstudy.mvp.application.dto;

import java.util.List;

public class ReplyPageResultDto {

    private List<ReplyDto> dtoList;

    private int totalPage;

    private int page;

    private int size;

    private int startPage, endPage;

    private boolean hasPrev, hasNext;

    private List<Integer> pageList;
}