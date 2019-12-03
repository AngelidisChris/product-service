package service.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.errorHandler.ErrorDetails;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    SingletonProductData singletonProductData = SingletonProductData.getInstance();

    public ProductController() throws ParseException {
    }

    //  display all products
    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<Object> index()
    {

        List<Product> productList= singletonProductData.fetchProducts();

        if (productList.isEmpty())
            return new ResponseEntity<>(productList, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(productList, HttpStatus.OK);
    }


//  find product by ID
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> show(@Valid @PathVariable String id) throws NumberFormatException {

        Product product = null;
        Long productId = null;

        try {
            productId = Long.parseLong(id);
        }
        catch (NumberFormatException  e)
        {
            return new ResponseEntity<>(new ErrorDetails(new Date(), "Id must be a Long type value", HttpStatus.BAD_REQUEST.value()).toString(), HttpStatus.BAD_REQUEST);
        }


        product =  singletonProductData.getProductById(productId);

        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>("[]", HttpStatus.NOT_FOUND);
    }

    //  search product by name
    @GetMapping(path = "/searchByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchByName(@Valid @RequestParam(required = true) String name){

        List<Product> productList = singletonProductData.searchProductsByName(name);
        if (!productList.isEmpty())
            return new ResponseEntity<>(singletonProductData.searchProductsByName(name), HttpStatus.OK);
        else
            return new ResponseEntity<>("[]", HttpStatus.NOT_FOUND);
    }

    //  search by product code
    @GetMapping(path = "/searchByCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchByCode(@Valid @RequestParam(required = true) String productCode){

        List<Product> productList = singletonProductData.searchProductsByCode(productCode);
        if (!productList.isEmpty())
            return new ResponseEntity<>(productList, HttpStatus.OK);
        else
            return new ResponseEntity<>("[]", HttpStatus.NOT_FOUND);
    }

    //  search by product price
    @GetMapping(path = "/searchByPrice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchByCode(@Valid @RequestParam(required = false) String min, @RequestParam(required = false) String max, @RequestParam(required = false) String sort){

        BigDecimal minValue=null;
        BigDecimal maxValue=null;


        try {
            if(min != null)
                minValue = new BigDecimal(min);
            if (max != null)
                maxValue = new BigDecimal(max);
        }
        catch (NumberFormatException  e)
        {
            return new ResponseEntity<>(new ErrorDetails(new Date(), "Min and Max values must be valid Decimal type values", HttpStatus.BAD_REQUEST.value()).toString(), HttpStatus.BAD_REQUEST);
        }

        if (sort != null && !sort.toLowerCase().equals("asc") && !sort.toLowerCase().equals("desc") )
            return new ResponseEntity<>(new ErrorDetails(new Date(), "Sort value must be ASC or DESC", HttpStatus.BAD_REQUEST.value()).toString(), HttpStatus.BAD_REQUEST);

        List<Product> productList = singletonProductData.searchProductsByPrice(minValue, maxValue, sort);

        if (!productList.isEmpty())
            return new ResponseEntity<>(productList, HttpStatus.OK);
        else
            return new ResponseEntity<>("[]", HttpStatus.NOT_FOUND);
    }


    //  add a new product
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> create(@Valid @RequestBody Product product){
        singletonProductData.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    //  deactivate product with given id
    @PutMapping(path = "/{id}/deactivate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deactivate(@Valid @PathVariable String id) throws NumberFormatException
    {

        Long productId = null;

        try {
            productId = Long.parseLong(id);
        }
        catch (NumberFormatException  e)
        {
            return new ResponseEntity<>(new ErrorDetails(new Date(), "Id must be a Long type value", HttpStatus.BAD_REQUEST.value()).toString(), HttpStatus.BAD_REQUEST);
        }

        singletonProductData.deactivateProduct(productId);
        return new ResponseEntity("[]", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean delete(@Valid @PathVariable String id)
    {
        Long productId = Long.parseLong(id);
        return singletonProductData.deleteProduct(productId);
    }
}
