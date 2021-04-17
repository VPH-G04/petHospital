package com.ecnu.petHospital.service;

import com.ecnu.petHospital.entity.TestPaper;
import com.ecnu.petHospital.param.PageParam;
import com.ecnu.petHospital.param.PaperParam;
import com.ecnu.petHospital.vo.PaperVO;
import com.github.pagehelper.PageInfo;

public interface TestPaperService {

    PageInfo<TestPaper> getTestPaperList(PageParam pageParam);

    boolean createTestPaper(PaperParam paperParam);

    PaperVO getTestPaper(Integer id);

    boolean deleteTestPaper(Integer id);
}
