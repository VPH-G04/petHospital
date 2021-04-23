package com.ecnu.petHospital.service;

import com.ecnu.petHospital.entity.TestPaper;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.PaperParam;
import com.ecnu.petHospital.vo.PaperVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TestPaperService {

    PageInfo<TestPaper> getTestPaperList(PageParam pageParam);

    List<TestPaper> getAllPaper();

    boolean createTestPaper(PaperParam paperParam);

    PaperVO getTestPaper(Integer id);

    boolean deleteTestPaper(Integer id);
}
