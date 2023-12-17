package co.agpaytech.rest;

import co.agpaytech.dto.CountryAddResponse;
import co.agpaytech.dto.ResponseCodeEnum;
import co.agpaytech.dto.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);
    static String countriesList[] = {"Afghanistan" ,"Albania" ,"Algeria" ,"Andorra" ,"Angola" ,"Antigua and Barbuda" ,"Argentina" ,"Armenia" ,"Australia" ,"Austria" ,"Azerbaijan",
            "Bahamas","Bahrain", "Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bhutan","Bolivia","Bosnia and Herzegovina","Botswana","Brazil","Brunei",
            "Bulgaria", "Burkina Faso","Burundi","Cabo Verde","Cambodia","Cameroon","Canada","Central African Republic (CAR)","Chad","Chile","China","Colombia","Comoros", "Congo, Democratic Republic of the","Congo, Republic of the","Costa Rica",
            "Cote d'Ivoire","Croatia","Cuba","Cyprus","Czechia","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea" ,
            "Eritrea","Estonia","Eswatini","Ethiopia","Fiji","Finland","France","Gabon","Gambia","Georgia","Germany","Ghana","Greece","Grenada","Guatemala","Guinea","Guinea-Bissau",
            "Guyana","Haiti","Honduras","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati",
            "Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Madagascar","Malawi","Malaysia" ,"Maldives" ,
            "Mali", "Malta" , "Marshall Islands" ,"Mauritania","Mauritius" ,"Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Morocco","Mozambique","Myanmar",
            "Namibia","Nauru" ,"Nepal","Netherlands","New Zealand","Nicaragua","Niger","Nigeria","North Korea","North Macedonia","Norway","Oman","Pakistan" ,"Palau","Palestine" ,
            "Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Romania" ,"Russia" ,"Rwanda","Saint Kitts and Nevis","Saint Lucia","Saint Vincent and the Grenadines" ,
            "Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands" ,
            "Somalia","South Africa","South Korea","South Sudan","Spain","Sri Lanka","Sudan","Suriname","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand" ,
            "Timor-Leste","Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan" ,"Tuvalu","Uganda","Ukraine" ,"United Arab Emirates (UAE)","United Kingdom (UK)","United States of America (USA)" ,
            "Uruguay","Uzbekistan","Vanuatu" ,"Vatican City (Holy See)" ,"Venezuela","Vietnam","Yemen","Zambia","Zimbabwe"
    };

    @Value("${lastname}")
    private  String lastname;

    @Value("${firstname}")
    private String username;

    private List<String> countries;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    public CountryController() {
        countries = new ArrayList<>(Arrays.asList(countriesList)) ;
    }

    @PostMapping("/countries")
    public ResponseEntity<ResponseMessage> getCountries(@RequestParam("pgNo") Optional<Integer>  pageNo, @RequestParam("pgSize") Optional<Integer>  pgSize){

        ResponseMessage responseMessage = new ResponseMessage();
        ResponseEntity<ResponseMessage> responseEntity;

        if (!pageNo.isPresent() || pageNo.get() < 0 || pgSize.get() < 0  )
        {
            responseMessage.setCode(ResponseCodeEnum.NOT_ALLOWED.getValue());
            responseMessage.setMessage(ResponseCodeEnum.NOT_ALLOWED.getDescription());
            responseEntity = new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }

        /**
         * Todo:
         * Implement Logic to restrict page count/size to avoid OutOfBounds exception
         * */

        Integer listSize = countries.size();
        Integer pageSize = pgSize.orElseGet(()-> {  Integer size = DEFAULT_PAGE_SIZE; return size;});
        Integer startIndex = pageNo.get();
        Integer endIndex = startIndex + pageSize;
        LOGGER.info("Page Size: {} Start Index: {}", pageSize, startIndex);
        List<String> pagedList = countries.subList(startIndex, endIndex);

        responseMessage.setCountries(pagedList);
        responseMessage.setCode(ResponseCodeEnum.SUCCESS.getValue());
        responseEntity = new ResponseEntity<>(responseMessage, HttpStatus.OK);

        return responseEntity;
    }

    @PostMapping("/addCountry/{country}")
    public ResponseEntity<CountryAddResponse> addCountry(@PathVariable("country") String countryName ){

        CountryAddResponse responseMessage = new CountryAddResponse();
        ResponseEntity<CountryAddResponse> responseEntity ;
        String countryNamePattern = "^([A-Za-z]+\\s*[A-Za-z]*)+$";

        if(countryName == null || !countryName.matches(countryNamePattern))
        {
            responseMessage.setCode(ResponseCodeEnum.NOT_ALLOWED.getValue());
            responseMessage.setMessage(ResponseCodeEnum.NOT_ALLOWED.getDescription());
            responseEntity = new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }

        if (countries.contains(countryName))
        {
            responseMessage.setCode(ResponseCodeEnum.SUCCESS.getValue());
            responseMessage.setMessage(String.format( "Country %s already exists.", countryName));
            return new ResponseEntity<>(responseMessage,HttpStatus.CONFLICT);
        };

        countries.add(countryName);
        responseMessage.setCode(ResponseCodeEnum.SUCCESS.getValue());
        responseMessage.setMessage(ResponseCodeEnum.SUCCESS.getDescription());

        responseEntity = new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
        return responseEntity;
    }

    @PostMapping("/search/{pattern}")
    public ResponseEntity<ResponseMessage> searchCountry( @NonNull @PathVariable("pattern") String pattern){

        ResponseMessage responseMessage = new ResponseMessage();
        ResponseEntity<ResponseMessage> responseEntity ;

        String searchPattern = "^([A-Za-z]+\\s*[A-Za-z]*)+$";


        if(pattern == null ||! pattern.matches(searchPattern) )
        {
            responseMessage.setCode(ResponseCodeEnum.NOT_ALLOWED.getValue());
            responseMessage.setMessage(ResponseCodeEnum.NOT_ALLOWED.getDescription());
            responseEntity = new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);

            return responseEntity;

        }

         String foundCountry = countries.stream().filter((country)->{return country.contains(pattern);}).findAny().orElseGet(()->{  String s = "Not Found"; return  s;});
         responseMessage.setCode(ResponseCodeEnum.SUCCESS.getValue());
         responseMessage.setMessage(foundCountry);

         responseEntity = new ResponseEntity<>(responseMessage, HttpStatus.OK);
        return responseEntity;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
