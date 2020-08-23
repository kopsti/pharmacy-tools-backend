package gr.appleton.ms.pharmacytools.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class Search Criteria used for querying retrievals.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;

}
