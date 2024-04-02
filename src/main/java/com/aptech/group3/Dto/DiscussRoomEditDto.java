package com.aptech.group3.Dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussRoomEditDto {

	private Long id;

	@NotEmpty(message="{label.name.error}")
	@NotNull(message="{label.name.error}")
	private String name;
	
	@NotEmpty(message="{discuss.topic.error}")
	@NotNull(message="{discuss.topic.error}")
	private String topic;
	

	private String type;
	

	private String expried_date;




}
