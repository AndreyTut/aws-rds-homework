package ua.study.my.awsrdshw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.study.my.awsrdshw.entity.ImageInfo;

@Repository
public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long> {
    @Modifying
    @Transactional
    @Query("delete from ImageInfo info where info.name = :name")
    void deleteByName(@Param("name") String name);

    @Query("select info from ImageInfo info where info.name = :name")
    ImageInfo findByName(@Param("name") String name);
}
