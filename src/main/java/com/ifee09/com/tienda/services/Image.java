package com.ifee09.com.tienda.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ifee09.com.tienda.models.Productos;

public class Image {
	
	public Image() {
		
	}
	public static String createNameImage(Productos p) {
		//nombre de la imagen con formato
		String nombre = p.getNombre()+"-1";
		String formato = ".png";
		//validamos si el nombre de la imagen ya existe
		File isruta = new File("src/main/resources/static/images/"+nombre+formato);
		//si el nombre existe le suma 1 a su numero
		if(isruta.exists()) {
			while(isruta.exists()) {
				String [] imagen = nombre.split("-");
				nombre = imagen[0]+"-"+(Integer.parseInt(imagen[1])+1);
				isruta = new File("src/main/resources/static/images/"+nombre+formato);
			}
		}
		return nombre+formato;
	}
	public static boolean writeImage(MultipartFile file,String imagen) {
		try {
			byte[] data = file.getBytes();
			//inserta la imagen en el disco
			FileOutputStream fot = new FileOutputStream("src/main/resources/static/images/"+imagen);
			fot.write(data);
			fot.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static boolean deleteImage(String imagen){
		//inserta la imagen en el disco
		File fot = new File("src/main/resources/static/images/"+imagen);
		if(fot.delete())return true;
		else return false;
		
	}
	public static String createRuteImage(String imagen,HttpServletRequest hsr) {
		//crea una ruta para la imagen en el servidor
		File ruta = new File("/images/"+imagen);
		String[] hostName = hsr.getRequestURL().toString().split("/");
		return  hostName[0]+"//"+hostName[2]+ruta.getAbsolutePath();
	}
	public static String getNameImage(String rutaImagenHost) {
		String[] rutasImage = rutaImagenHost.split("/");
		return rutasImage[4];
	}
}
