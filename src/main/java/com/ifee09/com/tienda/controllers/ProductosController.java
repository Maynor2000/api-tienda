package com.ifee09.com.tienda.controllers;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ifee09.com.tienda.models.Productos;
import com.ifee09.com.tienda.repositorys.ProductosRespository;
import com.ifee09.com.tienda.services.Image;

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
	public EntityModel<?> posProductos(@ModelAttribute Productos p,MultipartFile file
			,HttpServletRequest hsr) {
		//crea un nombre para la imagen con formato
		String nombreimagen = Image.createNameImage(p);
		//guarda la imagen en el disco
		Image.writeImage(file,nombreimagen);
		//coloca la ruta de la imagen
		p.setFoto(Image.createRuteImage(nombreimagen,hsr));
		//coloca una fecha a la imagen
		p.setFecha(LocalDate.now());
			
		return EntityModel.of(pr.save(p));
	}
	@PutMapping("/{id}")
	public EntityModel<?> updateProductos(@PathVariable("id") Integer id
			,@ModelAttribute Productos p,MultipartFile mf){
		return pr.findById(id).map(data->{
			data.setNombre(p.getNombre());
			//buscamos la imagen anterior y la borramos
			String nombreImage = Image.getNameImage(data.getFoto());//retorna el nombre de la imagen con formato
			Image.deleteImage(nombreImage);//elimanos la imagen
			Image.writeImage(mf,nombreImage);//escribimos la nueva imagen en el disco con el mismo nombre
			//-----------------------------------------
			data.setPrecio(p.getPrecio());
			data.setDescripcionC(p.getDescripcionC());
			data.setDescripcionL(p.getDescripcionL());
			return EntityModel.of(pr.save(data));
		}).orElseGet(()->{
			return EntityModel.of(pr.save(p));
		});
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?>deleteProductos(@PathVariable("id") Integer id){
		//buscamos la entidad por id para ser eliminada
		Productos p = pr.findById(id).get();
		//obtenemos el nombre con fromato de la imagen
		String nombreImagen = Image.getNameImage(p.getFoto());
		//si la imagen fue eliminada eliminamos la entidad
		if(Image.deleteImage(nombreImagen)) {
			pr.delete(p);
			//retornamos el codigo http
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
