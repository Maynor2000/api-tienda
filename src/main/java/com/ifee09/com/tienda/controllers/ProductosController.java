package com.ifee09.com.tienda.controllers;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ifee09.com.tienda.models.Productos;
import com.ifee09.com.tienda.models.ResponseHttp;
import com.ifee09.com.tienda.repositorys.ProductosRespository;
import com.ifee09.com.tienda.security.Security;
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
	public Object getProductos(@RequestParam(value="categoria"
	,required=false)String categoria,@RequestParam(value="token"
	,required=false)String token) {
		if(categoria==null) {
			if(Security.aut(token))return CollectionModel.of(pr.findAll());
			else {
				ResponseHttp response = new ResponseHttp("Unauthorized",401,"requiere autenticacion",LocalDateTime.now());
				return new ResponseEntity<ResponseHttp>(response,HttpStatus.UNAUTHORIZED);
			}
		}
		else {
			if(Security.aut(token))return CollectionModel.of(pr.findBycategoria(categoria));
			else {
				ResponseHttp response = new ResponseHttp("Unauthorized",401,"requiere autenticacion",LocalDateTime.now());
				return new ResponseEntity<ResponseHttp>(response,HttpStatus.UNAUTHORIZED);
			}
			
		}
	}

	@GetMapping("/{id}")
	public Object getById(@PathVariable("id")Integer id
	,@RequestParam(value="token",required=false)String token){
		if(Security.aut(token))return EntityModel.of(pr.findById(id));
		else {
			ResponseHttp response = new ResponseHttp("Unauthorized",401,"requiere autenticacion",LocalDateTime.now());
			return new ResponseEntity<ResponseHttp>(response,HttpStatus.UNAUTHORIZED);
		}
		
	}
	@PostMapping("")
	public Object posProductos(@ModelAttribute Productos p,MultipartFile file
			,HttpServletRequest hsr,@RequestParam(value="token"
			,required=false)String token) {
		if(Security.aut(token)) {
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
		else {
			ResponseHttp response = new ResponseHttp("Unauthorized",401,"requiere autenticacion",LocalDateTime.now());
			return new ResponseEntity<ResponseHttp>(response,HttpStatus.UNAUTHORIZED);
		}
	}
	@PutMapping("/{id}")
	public Object updateProductos(@PathVariable("id") Integer id
			,@ModelAttribute Productos p,MultipartFile mf,@RequestParam(value="token"
			,required=false)String token){
		if(Security.aut(token)) {
			return pr.findById(id).map(data->{
				data.setNombre(p.getNombre());
				//buscamos la imagen anterior y la borramos
				String nombreImage = Image.getNameImage(data.getFoto());//retorna el nombre de la imagen con formato
				Image.deleteImage(nombreImage);//elimanos la imagen
				Image.writeImage(mf,nombreImage);//escribimos la nueva imagen en el disco con el mismo nombre
				//-----------------------------------------
				data.setNombre(p.getNombre());
				data.setPrecio(p.getPrecio());
				data.setCategoria(p.getCategoria());
				data.setDescripcionC(p.getDescripcionC());
				data.setDescripcionL(p.getDescripcionL());
				return EntityModel.of(pr.save(data));
			}).orElseGet(()->{
				return EntityModel.of(pr.save(p));
			});
		}
		else {
			ResponseHttp response = new ResponseHttp("Unauthorized",401,"requiere autenticacion",LocalDateTime.now());
			return new ResponseEntity<ResponseHttp>(response,HttpStatus.UNAUTHORIZED);
		}
	}
	@DeleteMapping("/{id}")
	public Object deleteProductos(@PathVariable("id") Integer id,@RequestParam(value="token"
			,required=false)String token){
		if(Security.aut(token)) {
			//buscamos la entidad por id para ser eliminada
			Productos p = pr.findById(id).get();
			//obtenemos el nombre con fromato de la imagen
			String nombreImagen = Image.getNameImage(p.getFoto());
			pr.delete(p);
			//si la imagen fue eliminada eliminamos la entidad
			if(Image.deleteImage(nombreImagen)) {
				//retornamos el codigo http
				ResponseHttp response = new ResponseHttp("ok",200,"se elimino correctamente",LocalDateTime.now());
				return new ResponseEntity<ResponseHttp>(response,HttpStatus.OK);
			}else {
				ResponseHttp response = new ResponseHttp("Not Acceptable",406,"no se encuentra la imagen asociada a la entidad",LocalDateTime.now());
				return new ResponseEntity<ResponseHttp>(response,HttpStatus.NOT_ACCEPTABLE);
			}
		}
		else {
			ResponseHttp response = new ResponseHttp("Unauthorized",401,"requiere autenticacion",LocalDateTime.now());
			return new ResponseEntity<ResponseHttp>(response,HttpStatus.UNAUTHORIZED);
		}
	}
}
