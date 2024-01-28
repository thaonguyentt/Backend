package base.api.common;

import base.api.common.internal.entity.Area;
import base.api.common.internal.entity.AreaType;
import base.api.common.internal.mapper.AreaMapper;
import base.api.common.internal.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import base.api.common.internal.entity.Area;
import base.api.common.internal.entity.AreaType;
import base.api.common.internal.mapper.AreaMapper;
import base.api.common.internal.repository.AreaRepository;

import java.util.List;

@Service
public class AreaService {

  @Autowired
  AreaRepository areaRepository;
  @Autowired
  AreaMapper areaMapper;

  public List<AreaDTO> getAllProvinces() {
    List<Area> provinceEntities = areaRepository.findAllByType(AreaType.PROVINCE.getCode());
    var dtoList = areaMapper.toDto(provinceEntities);
    return dtoList;
  }

  public List<AreaDTO> getAllDistrictsOfProvince(String provinceCode) {
    return areaMapper.toDto(areaRepository.listDistrictsByProvinceCode(provinceCode));
  }

  public List<AreaDTO> getAllPrecinctOfProvinceOfDistrict(String provinceCode, String districtCode) {
    return areaMapper.toDto(areaRepository.listPrecinctByProvinceCodeAndDistrictCode(provinceCode, districtCode));
  }

  public AreaDTO getAreaByCode(String code) {
    return areaMapper.toDto(areaRepository.getByCode(code));
  }

  public List<String> getChildrenAreaCode(String parentCode) {

    return areaRepository.getChildrenAreaCodeByParentCode(parentCode);
  }
}
