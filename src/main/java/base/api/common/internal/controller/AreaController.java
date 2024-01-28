package base.api.common.internal.controller;

import base.api.common.AreaDTO;
import base.api.common.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AreaController {

  @Autowired
  AreaService areaService;

  @GetMapping("areas/lookup")
  public List<AreaDTO> getProvinces() {
    return areaService.getAllProvinces();
  }

  @GetMapping("/areas/lookup/{code1}")
  public List<AreaDTO> getDistrictsByProvinceCode(@PathVariable("code1") String code1) {
    var areaList = areaService.getAllDistrictsOfProvince(code1);
    return areaList;
  }

  @GetMapping("/areas/lookup/{code1}/{code2}")
  public List<AreaDTO> getPrecinctsByProvinceCodeAndDistrictCode(
    @PathVariable("code1") String code1,
    @PathVariable("code2") String code2
  ) {
    var areaList = areaService.getAllPrecinctOfProvinceOfDistrict(code1, code2);
    return areaList;
  }

  @GetMapping("/areas/{code}")
  public AreaDTO getByCode(@PathVariable("code") String code) {
    var areaDTO = areaService.getAreaByCode(code);
    if (areaDTO == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return areaDTO;
  }
}
