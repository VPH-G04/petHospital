package com.ecnu.petHospital.service.impl;

import com.ecnu.petHospital.dao.CaseMapper;
import com.ecnu.petHospital.dao.CasePictureMapper;
import com.ecnu.petHospital.dao.CaseVideoMapper;
import com.ecnu.petHospital.entity.Case;
import com.ecnu.petHospital.entity.CasePicture;
import com.ecnu.petHospital.entity.CaseVideo;
import com.ecnu.petHospital.enums.FileType;
import com.ecnu.petHospital.enums.ProcedureType;
import com.ecnu.petHospital.param.*;
import com.ecnu.petHospital.service.CaseService;
import com.ecnu.petHospital.vo.CaseVO;
import com.ecnu.petHospital.vo.ProcedureVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    CaseMapper caseMapper;

    @Autowired
    CasePictureMapper casePictureMapper;

    @Autowired
    CaseVideoMapper caseVideoMapper;

    @Override
    public CaseVO createCase(Case newcase, CaseParam caseParam) {

        caseMapper.insert(newcase);
        Integer id = newcase.getId();
        System.out.println(id);

        List<String> image = caseParam.getImage();
        if (image != null)
            insertPicOrVid(image, id, caseParam.getImageProcedure(), caseParam.getImageDescription(), FileType.Image);

        List<String> video = caseParam.getVideo();
        if (video != null)
            insertPicOrVid(video, id, caseParam.getVideoProcedure(), caseParam.getVideoDescription(), FileType.Video);

        return getCaseVO(newcase, caseParam);

    }

    private CaseVO getCaseVO(Case newcase, CaseParam caseParam) {
        CaseVO caseVO = new CaseVO();

        caseVO.setId(newcase.getId());
        caseVO.setName(newcase.getName());
        caseVO.setDisease_id(newcase.getDisease_id());

        List<ImageVideoParam> cImage = new ArrayList<>();
        List<ImageVideoParam> dImage = new ArrayList<>();
        List<ImageVideoParam> eImage = new ArrayList<>();
        List<ImageVideoParam> tImage = new ArrayList<>();
        List<ImageVideoParam> cVideo = new ArrayList<>();
        List<ImageVideoParam> dVideo = new ArrayList<>();
        List<ImageVideoParam> eVideo = new ArrayList<>();
        List<ImageVideoParam> tVideo = new ArrayList<>();

        for (int i = 0; i < caseParam.getImage().size(); i++) {
            String procedure = caseParam.getImageProcedure().get(i);
            String image = caseParam.getImage().get(i);
            String descirption = caseParam.getImageDescription().get(i);

            if (procedure.equals(ProcedureType.consultation)) {
                cImage.add(new ImageVideoParam(image, descirption));
            } else if (procedure.equals(ProcedureType.diagnosis)) {
                dImage.add(new ImageVideoParam(image, descirption));
            } else if (procedure.equals(ProcedureType.examination)) {
                eImage.add(new ImageVideoParam(image, descirption));
            } else {
                tImage.add(new ImageVideoParam(image, descirption));
            }
        }

        for (int i = 0; i < caseParam.getVideoDescription().size(); i++) {
            String procedure = caseParam.getVideoProcedure().get(i);
            String video = caseParam.getVideo().get(i);
            String descirption = caseParam.getVideoDescription().get(i);

            if (procedure.equals(ProcedureType.consultation)) {
                cVideo.add(new ImageVideoParam(video, descirption));
            } else if (procedure.equals(ProcedureType.diagnosis)) {
                dVideo.add(new ImageVideoParam(video, descirption));
            } else if (procedure.equals(ProcedureType.examination)) {
                eVideo.add(new ImageVideoParam(video, descirption));
            } else {
                tVideo.add(new ImageVideoParam(video, descirption));
            }
        }

        ProcedureVO procedureVO = new ProcedureVO();
        procedureVO.setProcedureName(ProcedureType.consultation);
        procedureVO.setDescribe(newcase.getConsultation());
        procedureVO.setImages(cImage);
        procedureVO.setVideos(cVideo);

        caseVO.getProcedureVOS().add(procedureVO);

        ProcedureVO procedureVO1 = new ProcedureVO();
        procedureVO1.setProcedureName(ProcedureType.examination);
        procedureVO1.setDescribe(newcase.getExamination());
        procedureVO1.setImages(eImage);
        procedureVO1.setVideos(eVideo);

        caseVO.getProcedureVOS().add(procedureVO1);

        ProcedureVO procedureVO2 = new ProcedureVO();
        procedureVO2.setProcedureName(ProcedureType.diagnosis);
        procedureVO2.setDescribe(newcase.getDiagnosis());
        procedureVO2.setImages(dImage);
        procedureVO2.setVideos(dVideo);

        caseVO.getProcedureVOS().add(procedureVO2);

        ProcedureVO procedureVO3 = new ProcedureVO();
        procedureVO3.setProcedureName(ProcedureType.treatment);
        procedureVO3.setDescribe(newcase.getTreatment());
        procedureVO3.setImages(tImage);
        procedureVO3.setVideos(tVideo);

        caseVO.getProcedureVOS().add(procedureVO3);

        return caseVO;
    }

    private void insertPicOrVid(List<String> tmp, Integer id, List<String> procedure, List<String> description, String type) {
        for (int i = 0; i < tmp.size(); i++) {
            if (type.equals(FileType.Image)) {
                CasePicture cp = new CasePicture();
                cp.setCase_id(id);
                cp.setProcedure(procedure.get(i));
                cp.setUrl(tmp.get(i));
                cp.setDescribe(description.get(i));
                casePictureMapper.insertPicture(cp);
            } else {
                CaseVideo cv = new CaseVideo();
                cv.setCase_id(id);
                cv.setProcedure(procedure.get(i));
                cv.setUrl(tmp.get(i));
                cv.setDescribe(description.get(i));
                caseVideoMapper.insertVideo(cv);
            }

        }
    }

    @Override
    public Integer updateCase(Case newcase) {
        return caseMapper.updateByPrimaryKey(newcase);
    }

    @Override
    public Integer deleteCase(Integer id) {


        casePictureMapper.deletePictureByCaseId(id);
        caseVideoMapper.deleteVideoByCaseId(id);

        return caseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CaseVO getCaseById(Integer id) {
        CaseVO caseVO = new CaseVO();
        Case aCase = caseMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(aCase, caseVO);


        List<CasePicture> imageList = casePictureMapper.getPicturesByCaseId(id);
        List<CaseVideo> videoList = caseVideoMapper.getVideosByCaseId(id);
        ProcedureVO cpvo = new ProcedureVO();
        ProcedureVO dpvo = new ProcedureVO();
        ProcedureVO epvo = new ProcedureVO();
        ProcedureVO tpvo = new ProcedureVO();

        for(int i=0; i<imageList.size(); i++){
            CasePicture tmp = imageList.get(i);
            if(tmp.getProcedure().equals(ProcedureType.consultation)) {
                cpvo.getImages().add(new ImageVideoParam(tmp.getUrl(),tmp.getDescribe()));
            }
            else if(tmp.getProcedure().equals(ProcedureType.diagnosis)){
                dpvo.getImages().add(new ImageVideoParam(tmp.getUrl(),tmp.getDescribe()));
            }
            else if(tmp.getProcedure().equals(ProcedureType.examination)){
                epvo.getImages().add(new ImageVideoParam(tmp.getUrl(),tmp.getDescribe()));
            }
            else {
                tpvo.getImages().add(new ImageVideoParam(tmp.getUrl(),tmp.getDescribe()));
            }

        }
        for(int i=0; i<videoList.size(); i++){
            CaseVideo tmp = videoList.get(i);
            if(tmp.getProcedure().equals(ProcedureType.consultation)) {
                cpvo.getVideos().add(new ImageVideoParam(tmp.getUrl(),tmp.getDescribe()));
            }
            else if(tmp.getProcedure().equals(ProcedureType.diagnosis)){
                dpvo.getVideos().add(new ImageVideoParam(tmp.getUrl(),tmp.getDescribe()));
            }
            else if(tmp.getProcedure().equals(ProcedureType.examination)){
                epvo.getVideos().add(new ImageVideoParam(tmp.getUrl(),tmp.getDescribe()));
            }
            else {
                tpvo.getVideos().add(new ImageVideoParam(tmp.getUrl(),tmp.getDescribe()));
            }

        }

        cpvo.setDescribe(aCase.getConsultation());
        cpvo.setProcedureName(ProcedureType.consultation);
        dpvo.setDescribe(aCase.getDiagnosis());
        dpvo.setProcedureName(ProcedureType.diagnosis);
        epvo.setDescribe(aCase.getExamination());
        epvo.setProcedureName(ProcedureType.examination);
        tpvo.setDescribe(aCase.getTreatment());
        tpvo.setProcedureName(ProcedureType.treatment);

        caseVO.getProcedureVOS().add(cpvo);
        caseVO.getProcedureVOS().add(dpvo);
        caseVO.getProcedureVOS().add(epvo);
        caseVO.getProcedureVOS().add(tpvo);
        return caseVO;
    }

    @Override
    public PageInfo<CaseListParam> getCasesByDiseaseId(Integer diseaseId, PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<CaseListParam> casesByDisId = caseMapper.getCasesByDisId(diseaseId);
        return new PageInfo<>(casesByDisId);
    }


    @Override
    public List<Case> getCases() {
        return caseMapper.selectAll();
    }
}
