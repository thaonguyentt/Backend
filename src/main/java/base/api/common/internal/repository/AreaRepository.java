package base.api.common.internal.repository;

import base.api.common.internal.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Integer> {
  List<Area> findAllByType(int type);
  Area getByCode(String code);

  @Query("""
    SELECT province FROM Area province WHERE province.type = 1
  """)
  List<Area> listProvinces(@Param("code") int code);

  @Query("""
    SELECT district 
    FROM Area province JOIN Area district ON district.parentAreaId = province.id
    WHERE 
      province.type = 1 AND province.code = :provinceCode
      AND district.type = 2 
  """)
  List<Area> listDistrictsByProvinceCode(
    @Param("provinceCode") String provinceCode
  );

  @Query("""
    SELECT precinct 
    FROM Area province 
      JOIN Area district ON district.parentAreaId = province.id
      JOIN Area precinct ON precinct.parentAreaId = district.id
    WHERE 
      province.type = 1 AND province.code = :provinceCode
      AND district.type = 2 AND district.code = :districtCode
      AND precinct.type = 3
  """)
  List<Area> listPrecinctByProvinceCodeAndDistrictCode(
    @Param("provinceCode") String provinceCode,
    @Param("districtCode") String districtCode
  );

  @Query(value = """
    with recursive a
        as (select id, code, name, parent_area_id
             from common_area
             where code = :parentAreaCode
             union all
             select c.id, c.code, c.name, c.parent_area_id
             from common_area c
                  inner join a ON a.id = c.parent_area_id
        )
    select distinct code from a;
  """, nativeQuery = true)
  List<String> getChildrenAreaCodeByParentCode(
    @Param("parentAreaCode") String parentCode);
}