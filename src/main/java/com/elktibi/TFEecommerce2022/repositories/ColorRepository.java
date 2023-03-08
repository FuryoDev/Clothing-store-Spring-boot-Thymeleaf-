package com.elktibi.TFEecommerce2022.repositories;

import com.elktibi.TFEecommerce2022.models.product.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

    Optional<Color> findColorByColorCode(String code);

    Optional<Color> findColorByName(String name);
}
