package com.learncode_backend.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.cloudinary.Cloudinary;
import com.learncode_backend.dto.AdminCourseDTO;
import com.learncode_backend.model.Course;
import com.learncode_backend.service.CloudinaryService;
import com.learncode_backend.service.CourseService;
import com.learncode_backend.utils.ApiResponse;


import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/admin/courses")
public class CourseAdminController {


	@Autowired
	private final CourseService courseService;


	@Autowired
	private final CloudinaryService cloudinaryService;


	@Autowired
	private ModelMapper mapper;


	public CourseAdminController(CourseService courseService, CloudinaryService cloudinaryService,
			ModelMapper modelMapper) {
		this.courseService = courseService;
		this.cloudinaryService = cloudinaryService;
		this.mapper = modelMapper;
	}


	@GetMapping
	public ResponseEntity<ApiResponse<List<AdminCourseDTO>>> listAll() throws Exception {


		List<AdminCourseDTO> lista = courseService.findAll().stream()
				.map(course -> mapper.map(course, AdminCourseDTO.class)).toList();


		ApiResponse<List<AdminCourseDTO>> response = new ApiResponse<>(true, "Listado de cursos", lista);


		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@GetMapping("/paged")
	public ResponseEntity<ApiResponse<Page<AdminCourseDTO>>> findPaged(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, 
			@RequestParam(required = false) String title,
			@RequestParam(required = false) Boolean published) throws Exception {


		Pageable pageable = PageRequest.of(page, size);


		Page<AdminCourseDTO> pagedResult = courseService.findPaged(pageable, title, published)
				.map(course -> mapper.map(course, AdminCourseDTO.class));
		ApiResponse<Page<AdminCourseDTO>> response = new ApiResponse<>(true, "Listado paginado de cursos", pagedResult);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@PostMapping
	public ResponseEntity<ApiResponse<AdminCourseDTO>> create(@Valid @RequestBody AdminCourseDTO dto) throws Exception {
		Course course = mapper.map(dto, Course.class);
		Course saved = courseService.save(course);


		AdminCourseDTO responseDto = mapper.map(saved, AdminCourseDTO.class);
		ApiResponse<AdminCourseDTO> response = new ApiResponse<>(true, "Curso creado correctamente", responseDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<AdminCourseDTO>> getById(@PathVariable UUID id) throws Exception{
		Course course = courseService.findById(id);
		
		AdminCourseDTO responseDto = mapper.map(course, AdminCourseDTO.class);
		ApiResponse<AdminCourseDTO> response = new ApiResponse<>(true, "Curso encontrado", responseDto);


		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<AdminCourseDTO>> update(	@PathVariable UUID id,
																@Valid @RequestBody AdminCourseDTO dto)throws Exception {


		Course course = mapper.map(dto, Course.class);
		Course updated = courseService.updateCourse(id, course);


		AdminCourseDTO responseDto = mapper.map(updated, AdminCourseDTO.class);
		ApiResponse<AdminCourseDTO> response = new ApiResponse<>(true, "¡Curso actualizado correctamente!",
				responseDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<AdminCourseDTO>> delete(@PathVariable UUID id) throws Exception{
		courseService.deleteByID(id);


		ApiResponse<AdminCourseDTO> response = new ApiResponse<>(true, "¡Curso eliminado!", null);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	/*
	@PostMapping("/{id}/upload/icon")
	public ResponseEntity<ApiResponse<AdminCourseDTO>> uploadIcon(@PathVariable UUID id,
			@RequestParam("file") MultipartFile file) throws Exception{


		String url = cloudinaryService.uploadImage(file);
		Course course = courseService.findById(id);
		course.setIconUrl(url);


		Course updated = courseService.updateCourse(id, course);
		AdminCourseDTO dto = mapper.map(updated, AdminCourseDTO.class);
		ApiResponse<AdminCourseDTO> response = new ApiResponse<>(true, "Icono actualizado", dto);


		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@PostMapping("/{id}/upload/cover")
	public ResponseEntity<ApiResponse<AdminCourseDTO>> uploadCover(@PathVariable UUID id,
			@RequestParam("file") MultipartFile file) throws Exception{


		String url = cloudinaryService.uploadImage(file);
		Course course = courseService.findById(id);
		course.setCoverUrl(url);


		Course updated = courseService.updateCourse(id, course);
		AdminCourseDTO responseDto = mapper.map(updated, AdminCourseDTO.class);
		ApiResponse<AdminCourseDTO> response = new ApiResponse<>(true, "Cover actualizado correctamente", responseDto);


		return ResponseEntity.status(HttpStatus.OK).body(response);
	}*/


}
