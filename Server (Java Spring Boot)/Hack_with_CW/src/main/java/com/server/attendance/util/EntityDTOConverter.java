package com.server.attendance.util;

import com.server.attendance.dto.*;
import com.server.attendance.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@SuppressWarnings("serial")
public class EntityDTOConverter {

    private EntityDTOConverter() {
    }

    private static ModelMapper getHODDtoMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<HoDDetail, HoDDetailDTO>() {
            @Override
            protected void configure() {
            }
        });
        return mapper;
    }

    public static HoDDetailDTO getHODDetailDTOFromEntity(HoDDetail hoDDetail) {
        return getHODDtoMapper().map(hoDDetail, HoDDetailDTO.class);
    }

    public static HoDDetail getHoDDetailEntityFromDTO(HoDDetailDTO hoDDetailDTO) {
        return new ModelMapper().map(hoDDetailDTO, HoDDetail.class);
    }

    private static ModelMapper getTeacherToMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<TeacherDetail, TeacherDetailDTO>() {
            @Override
            protected void configure() {
                skip(destination.getHoDDetailDTO());
            }
        });
        return mapper;
    }

    public static TeacherDetailDTO getTeacherDetailDTOFromEntity(TeacherDetail teacherDetail) {
        return getTeacherToMapper().map(teacherDetail, TeacherDetailDTO.class);
    }

    public static TeacherDetail getTeacherDetailEntityFromDTO(TeacherDetailDTO teacherDetailDTO) {
        return new ModelMapper().map(teacherDetailDTO, TeacherDetail.class);
    }

    private static ModelMapper getCourseToMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<CourseDetail, CourseDetailDTO>() {
            @Override
            protected void configure() {
            }
        });
        return mapper;
    }

    public static CourseDetailDTO getCourseDetailDTOFromEntity(CourseDetail courseDetail) {
        return getCourseToMapper().map(courseDetail, CourseDetailDTO.class);
    }

    public static CourseDetail getCourseDetailEntityFromDTO(CourseDetailDTO courseDetailDTO) {
        return new ModelMapper().map(courseDetailDTO, CourseDetail.class);
    }

    private static ModelMapper getStudentToMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<StudentDetail, StudentDetailDTO>() {
            @Override
            protected void configure() {
            }
        });
        return mapper;
    }

    public static StudentDetailDTO getStudentDetailDTOFromEntity(StudentDetail studentDetail) {
        return getStudentToMapper().map(studentDetail, StudentDetailDTO.class);
    }

    public static StudentDetail getStudentDetailEntityFromDTO(StudentDetailDTO studentDetailDTO) {
        return new ModelMapper().map(studentDetailDTO, StudentDetail.class);
    }

    private static ModelMapper getParentFatherToMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<FatherDetail, FatherDetailDTO>() {
            @Override
            protected void configure() {
            }
        });
        return mapper;
    }

    public static FatherDetailDTO getParentFatherDetailDTOFromEntity(FatherDetail fatherDetail) {
        return getParentFatherToMapper().map(fatherDetail, FatherDetailDTO.class);
    }

    public static FatherDetail getParentFatherDetailEntityFromDTO(FatherDetailDTO fatherDetailDTO) {
        return new ModelMapper().map(fatherDetailDTO, FatherDetail.class);
    }

    private static ModelMapper getParentMotherToMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<MotherDetail, MotherDetailDTO>() {
            @Override
            protected void configure() {
            }
        });
        return mapper;
    }

    public static MotherDetailDTO getParentMotherDetailDTOFromEntity(MotherDetail motherDetail) {
        return getParentMotherToMapper().map(motherDetail, MotherDetailDTO.class);
    }

    public static MotherDetail getParentMotherDetailEntityFromDTO(MotherDetailDTO motherDetailDTO) {
        return new ModelMapper().map(motherDetailDTO, MotherDetail.class);
    }

    private static ModelMapper getLectureToMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<LectureDetail, LectureDetailDTO>() {
            @Override
            protected void configure() {
            }
        });
        return mapper;
    }

    public static LectureDetailDTO getLectureDetailDTOFromEntity(LectureDetail lectureDetail) {
        return getLectureToMapper().map(lectureDetail, LectureDetailDTO.class);
    }

    public static LectureDetail getLectureDetailEntityFromDTO(LectureDetailDTO lectureDetailDTO) {
        return new ModelMapper().map(lectureDetailDTO, LectureDetail.class);
    }

    private static ModelMapper getAttendanceToMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<AttendanceDetail, AttendanceDetailDTO>() {
            @Override
            protected void configure() {
            }
        });
        return mapper;
    }

    public static AttendanceDetailDTO getAttendanceDetailDTOFromEntity(AttendanceDetail attendanceDetail) {
        return getAttendanceToMapper().map(attendanceDetail, AttendanceDetailDTO.class);
    }

    public static AttendanceDetail getAttendanceDetailEntityFromDTO(AttendanceDetailDTO attendanceDetailDTO) {
        return new ModelMapper().map(attendanceDetailDTO, AttendanceDetail.class);
    }

    private static ModelMapper getQuestionToMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<QuestionDetail, QuestionDetailDTO>() {
            @Override
            protected void configure() {
            }
        });
        return mapper;
    }

    public static QuestionDetailDTO getQuestionDetailDTOFromEntity(QuestionDetail questionDetail) {
        return getQuestionToMapper().map(questionDetail, QuestionDetailDTO.class);
    }

    public static QuestionDetail getQuestionDetailEntityFromDTO(QuestionDetailDTO questionDetailDTO) {
        return new ModelMapper().map(questionDetailDTO, QuestionDetail.class);
    }

}
