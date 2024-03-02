package base.api.common.internal.mapper;

import base.api.common.AreaDTO;
import base.api.common.internal.entity.Area;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-23T16:41:56+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240103-0614, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class AreaMapperImpl implements AreaMapper {

    @Override
    public Area toEntity(AreaDTO areaDTO) {
        if ( areaDTO == null ) {
            return null;
        }

        Area area = new Area();

        area.setAncestor( toEntity( areaDTO.ancestor() ) );
        area.setCode( areaDTO.code() );
        area.setId( areaDTO.id() );
        area.setName( areaDTO.name() );
        area.setType( areaDTO.type() );

        return area;
    }

    @Override
    public List<Area> toEntity(List<AreaDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Area> list1 = new ArrayList<Area>( list.size() );
        for ( AreaDTO areaDTO : list ) {
            list1.add( toEntity( areaDTO ) );
        }

        return list1;
    }

    @Override
    public AreaDTO toDto(Area area) {
        if ( area == null ) {
            return null;
        }

        Integer id = null;
        String code = null;
        String name = null;
        Integer type = null;
        List<AreaDTO> ancestor = null;

        id = area.getId();
        code = area.getCode();
        name = area.getName();
        type = area.getType();
        ancestor = toDto( area.getAncestor() );

        AreaDTO areaDTO = new AreaDTO( id, code, name, type, ancestor );

        return areaDTO;
    }

    @Override
    public List<AreaDTO> toDto(List<Area> list) {
        if ( list == null ) {
            return null;
        }

        List<AreaDTO> list1 = new ArrayList<AreaDTO>( list.size() );
        for ( Area area : list ) {
            list1.add( toDto( area ) );
        }

        return list1;
    }

    @Override
    public Area partialUpdate(AreaDTO areaDTO, Area area) {
        if ( areaDTO == null ) {
            return area;
        }

        if ( area.getAncestor() != null ) {
            List<Area> list = toEntity( areaDTO.ancestor() );
            if ( list != null ) {
                area.getAncestor().clear();
                area.getAncestor().addAll( list );
            }
        }
        else {
            List<Area> list = toEntity( areaDTO.ancestor() );
            if ( list != null ) {
                area.setAncestor( list );
            }
        }
        if ( areaDTO.code() != null ) {
            area.setCode( areaDTO.code() );
        }
        if ( areaDTO.id() != null ) {
            area.setId( areaDTO.id() );
        }
        if ( areaDTO.name() != null ) {
            area.setName( areaDTO.name() );
        }
        if ( areaDTO.type() != null ) {
            area.setType( areaDTO.type() );
        }

        return area;
    }
}
