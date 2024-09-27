package base.api.common.internal.controller;

import base.api.common.AreaDto;
import base.api.common.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://the-flying-bookstore.vercel.app"})
@RequestMapping("/api")
public class AreaController {

  @Autowired
  AreaService areaService;

  @GetMapping("areas/lookup")
  public List<AreaDto> getProvinces() {
    return areaService.getAllProvinces();
  }

  @GetMapping("/areas/lookup/{code1}")
  public List<AreaDto> getDistrictsByProvinceCode(@PathVariable("code1") String code1) {
    var areaList = areaService.getAllDistrictsOfProvince(code1);
    return areaList;
  }

  @GetMapping("/areas/lookup/{code1}/{code2}")
  public List<AreaDto> getPrecinctsByProvinceCodeAndDistrictCode(
    @PathVariable("code1") String code1,
    @PathVariable("code2") String code2
  ) {
    var areaList = areaService.getAllPrecinctOfProvinceOfDistrict(code1, code2);
    return areaList;
  }

  @GetMapping("/areas/{code}")
  public AreaDto getByCode(@PathVariable("code") String code) {
    var areaDTO = areaService.getAreaByCode(code);
    if (areaDTO == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return areaDTO;
  }
}
