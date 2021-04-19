package com.ecnu.petHospital.service.impl;

import com.ecnu.petHospital.dao.CaseMapper;
import com.ecnu.petHospital.dao.CasePictureMapper;
import com.ecnu.petHospital.dao.CaseVideoMapper;
import com.ecnu.petHospital.entity.Case;
import com.ecnu.petHospital.entity.CasePicture;
import com.ecnu.petHospital.entity.CaseVideo;
import com.ecnu.petHospital.enums.FileType;
import com.ecnu.petHospital.enums.ProcedureType;
import com.ecnu.petHospital.param.CaseListParam;
import com.ecnu.petHospital.param.CaseParam;
import com.ecnu.petHospital.service.CaseService;
import com.ecnu.petHospital.vo.CaseVO;
import com.ecnu.petHospital.vo.ProcedureVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<String> cImage = caseParam.getCImage();
        if(cImage!=null)
            insertPicorVid(cImage, id, ProcedureType.consultation, FileType.Image);
        List<String> dImage = caseParam.getDImage();
        if(dImage!=null)
            insertPicorVid(dImage, id, ProcedureType.diagnosis, FileType.Image);
        List<String> eImage = caseParam.getEImage();
        if(eImage!=null)
            insertPicorVid(eImage, id, ProcedureType.examination, FileType.Image);
        List<String> tImage = caseParam.getTImage();
        if(tImage!=null)
            insertPicorVid(tImage, id, ProcedureType.treatment, FileType.Image);

        List<String> cVideo = caseParam.getCVideo();
        if(cVideo!=null)
            insertPicorVid(cVideo, id, ProcedureType.consultation, FileType.Video);
        List<String> dVideo = caseParam.getDVideo();
        if(dVideo!=null)
            insertPicorVid(dVideo, id, ProcedureType.diagnosis, FileType.Video);
        List<String> eVideo = caseParam.getEVideo();
        if(eVideo!=null)
            insertPicorVid(eVideo, id, ProcedureType.examination, FileType.Video);
        List<String> tVideo = caseParam.getTVideo();
        if(tVideo!=null)
            insertPicorVid(tVideo, id, ProcedureType.treatment, FileType.Video);

        return getCaseVO(newcase,caseParam);

    }

    private void insertPicorVid(List<String> tmp, Integer id, String procedure, String type){
        for(int i=0; i<tmp.size(); i++){
            if(type.equals(FileType.Image)){
                CasePicture cp = new CasePicture();
                cp.setCase_id(id);
                cp.setProcedure(procedure);
                cp.setUrl(tmp.get(i));
                cp.setDescribe("image description");
                casePictureMapper.insertPicture(cp);
            }
            else{
                CaseVideo cv = new CaseVideo();
                cv.setCase_id(id);
                cv.setProcedure(procedure);
                cv.setUrl(tmp.get(i));
                cv.setDescribe("video description");
                caseVideoMapper.insertVideo(cv);
            }

        }
    }

    private CaseVO getCaseVO(Case newcase, CaseParam caseParam){
        CaseVO caseVO = new CaseVO();

        caseVO.setId(newcase.getId());
        caseVO.setName(newcase.getName());
        caseVO.setDisease_id(newcase.getDisease_id());

        ProcedureVO procedureVO = new ProcedureVO();
        procedureVO.setProcedureName(ProcedureType.consultation);
        procedureVO.setDescribe(newcase.getConsultation());
        procedureVO.setImages(caseParam.getCImage());
        procedureVO.setVideos(caseParam.getCVideo());

        caseVO.getProcedureVOS().add(procedureVO);

        ProcedureVO procedureVO1 = new ProcedureVO();
        procedureVO1.setProcedureName(ProcedureType.examination);
        procedureVO1.setDescribe(newcase.getExamination());
        procedureVO1.setImages(caseParam.getEImage());
        procedureVO1.setVideos(caseParam.getEVideo());

        caseVO.getProcedureVOS().add(procedureVO1);

        ProcedureVO procedureVO2 = new ProcedureVO();
        procedureVO2.setProcedureName(ProcedureType.diagnosis);
        procedureVO2.setDescribe(newcase.getDiagnosis());
        procedureVO2.setImages(caseParam.getDImage());
        procedureVO2.setVideos(caseParam.getDVideo());

        caseVO.getProcedureVOS().add(procedureVO2);

        ProcedureVO procedureVO3 = new ProcedureVO();
        procedureVO3.setProcedureName(ProcedureType.treatment);
        procedureVO3.setDescribe(newcase.getTreatment());
        procedureVO3.setImages(caseParam.getTImage());
        procedureVO3.setVideos(caseParam.getTVideo());

        caseVO.getProcedureVOS().add(procedureVO3);

        return caseVO;
    }
    @Override
    public Integer updateCase(Case newcase) {
        return caseMapper.updateByPrimaryKey(newcase);
    }

    @Override
    public Integer deleteCase(Integer id) {


        casePictureMapper.deletePictureByCaseId(id);

        return caseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CaseVO getCaseById(Integer id) {
        CaseVO caseVO = new CaseVO();
        Case aCase = caseMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(aCase,caseVO);


        List<CasePicture> imageList = casePictureMapper.getPicturesByCaseId(id);
        List<CaseVideo> videoList = caseVideoMapper.getVideosByCaseId(id);
        ProcedureVO cpvo = new ProcedureVO();
        ProcedureVO dpvo = new ProcedureVO();
        ProcedureVO epvo = new ProcedureVO();
        ProcedureVO tpvo = new ProcedureVO();

        for(int i=0; i<imageList.size(); i++){
            CasePicture tmp = imageList.get(i);
            if(tmp.getProcedure().equals(ProcedureType.consultation)) {
                cpvo.getImages().add(tmp.getUrl());
            }
            else if(tmp.getProcedure().equals(ProcedureType.diagnosis)){
                dpvo.getImages().add(tmp.getUrl());
            }
            else if(tmp.getProcedure().equals(ProcedureType.examination)){
                epvo.getImages().add(tmp.getUrl());
            }
            else {
                tpvo.getImages().add(tmp.getUrl());
            }

        }
        for(int i=0; i<videoList.size(); i++){
            CaseVideo tmp = videoList.get(i);
            if(tmp.getProcedure().equals(ProcedureType.consultation)) {
                cpvo.getVideos().add(tmp.getUrl());
            }
            else if(tmp.getProcedure().equals(ProcedureType.diagnosis)){
                dpvo.getVideos().add(tmp.getUrl());
            }
            else if(tmp.getProcedure().equals(ProcedureType.examination)){
                epvo.getVideos().add(tmp.getUrl());
            }
            else {
                tpvo.getVideos().add(tmp.getUrl());
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
    public List<CaseListParam> getCasesByDiseaseId(Integer diseaseId) {
        return caseMapper.getCasesByDisId(diseaseId);
    }

    @Override
    public List<Case> getCases() {
        return caseMapper.selectAll();
    }
}
