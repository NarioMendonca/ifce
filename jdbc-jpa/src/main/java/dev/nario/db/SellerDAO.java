package dev.nario.db;

import java.util.List;
import dev.nario.entities.Seller; // Certifique-se de importar a classe Seller corretamente

public interface SellerDAO {
    Seller insert(Seller seller);
    void update(Seller seller);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
}