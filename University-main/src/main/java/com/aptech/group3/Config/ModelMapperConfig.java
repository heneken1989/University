package com.aptech.group3.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aptech.group3.Dto.ClassSubjectDto;
import com.aptech.group3.entity.ClassForSubject;




@Configuration
public class ModelMapperConfig {

    @Bean
     ModelMapper modelMapper() {
        // Create ModelMapper object and configure it
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		/*
		 * modelMapper.createTypeMap(ClassForSubject.class, ClassSubjectDto.class)
		 * .addMapping(src -> src.getRoom().getName(), ClassSubjectDto::setRoomName)
		 * .addMapping(src -> src.getTeacher().getName(),
		 * ClassSubjectDto::setTeacherName) .addMapping(src ->
		 * src.getSemeter().getName(), ClassSubjectDto::setSemesterName) .addMapping(src
		 * -> src.getSubject().getName(), ClassSubjectDto::setSubjectName);
		 */

        return modelMapper;
    }
}
