package com.ifee09.com.tienda.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ifee09.com.tienda.models.Productos;
import com.ifee09.com.tienda.repositorys.ProductosRespository;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductosController {
	
	private final ProductosRespository pr;
	
	ProductosController(ProductosRespository pr){
		this.pr = pr;
	}
	
	@GetMapping("")
	public CollectionModel<?> getProductos() {
		
		return CollectionModel.of(pr.findAll());
	}
	
	@GetMapping("/{id}")
	public EntityModel<?> getById(@PathVariable("id")Integer id){
		
		return EntityModel.of(pr.findById(id));
	}
	@PostMapping("")
	public EntityModel<?> posProductos(@ModelAttribute Productos p,MultipartFile file) {
		
		try {
			
			byte[]data = file.getBytes();
			
			FileOutputStream fot = new FileOutputStream("src/main/resources/static/images/"+p.getNombre()+".jpg");
			
			fot.write(data);
			fot.close();
			
			File ruta = new File("/static/images/"+p.getNombre()+".jpg");
			p.setFoto(ruta.getAbsolutePath());
			p.setFecha(LocalDate.now());
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EntityModel.of(pr.save(p));
	}

}
