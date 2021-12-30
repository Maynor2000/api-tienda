package com.ifee09.com.tienda.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ifee09.com.tienda.models.*;

@Repository
public interface ProductosRespository extends JpaRepository<Productos,Integer>{
	public List<Productos> findBycategoria(String categoria);
}
