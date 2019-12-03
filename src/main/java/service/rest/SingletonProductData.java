package service.rest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// singleton class to test API
public class SingletonProductData {

//    list of products
    private List<Product> products;

//    create singleton instance
    private static SingletonProductData instance = null;
    public static SingletonProductData getInstance() throws ParseException {
        if (instance == null)
        {
            instance =new SingletonProductData();
        }
        return instance;
    }

    public SingletonProductData() throws ParseException {
        products = new ArrayList<Product>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));

//      add  new products in the list
        products.add(new Product(1L, "p1", new BigDecimal("1245.19"), "100", new Date(), formatter.parse("12-03-2021 13:10:22"), 1));
        products.add(new Product(2L, "p2", new BigDecimal("999.39"), "101", new Date(), formatter.parse("12-03-2021 13:10:22"), 1));
        products.add(new Product(3L, "p3", new BigDecimal("764.49"), "102", new Date(), formatter.parse("12-03-2021 13:10:22"), 1));
        products.add(new Product(4L, "p4", new BigDecimal("846.09"), "103", new Date(), formatter.parse("12-03-2021 13:10:22"), 1));
        products.add(new Product(5L, "p5", new BigDecimal("1890.11"), "104", new Date(), formatter.parse("12-03-2021 13:10:22"), 1));
        products.add(new Product(6L, "p6", new BigDecimal("56.56"), "105", new Date(), formatter.parse("12-03-2021 13:10:22"),1 ));
    }

// return all products
    public List<Product> fetchProducts() {
        return products;
    }

//    return product by id or null
    public Product getProductById(Long id)
    {
        for (Product p: products) {
            if (p.getId() == id)
            {
                return p;
            }
        }
        return null;
    }

//  Search Product by name
    public List<Product> searchProductsByName(String searchString) {
        List<Product> matchedProducts = new ArrayList<Product>();
        for (Product p: products) {
            if(searchString != "" && p.getName().toLowerCase().contains(searchString.toLowerCase()))
            {
                matchedProducts.add(p);
            }
        }

        return matchedProducts;
    }

//  Search by product code
    public List<Product> searchProductsByCode(String searchString) {
        List<Product> matchedProducts = new ArrayList<Product>();
        for (Product p: products) {
            if(searchString != "" && p.getProductCode().toLowerCase().contentEquals(searchString.toLowerCase()))
            {
                matchedProducts.add(p);
            }
        }
        return matchedProducts;
    }

    //  Search by product price
    public List<Product> searchProductsByPrice(BigDecimal min, BigDecimal max, String sort) {
        ArrayList<Product> matchedProducts = new ArrayList<Product>();

//        min default value is 0.0
        if (min == null)
        {
            min = BigDecimal.valueOf(0.00);
        }
//      no max default value
        if (max == null)
        {
            for (Product p: products) {
                if((p.getPrice().compareTo(min) == 1 || p.getPrice().compareTo(min) == 0))
                {
                    matchedProducts.add(p);
                }
            }
        }
        else
        {
            for (Product p: products) {
                if((p.getPrice().compareTo(min) == 1 || p.getPrice().compareTo(min) == 0) && (p.getPrice().compareTo(max) == -1 || p.getPrice().compareTo(max) == 0))
                {
                    matchedProducts.add(p);
                }
            }
        }

        if (sort != null)
        {
            // ascending sorting
            if (sort.toLowerCase().equals("asc")) {
                Collections.sort(matchedProducts,
                        (product1, product2) -> product1.getPrice().compareTo(product2.getPrice()));
            }
            // descending sorting
            else if (sort.toLowerCase().equals("desc"))
            {
                Collections.sort(matchedProducts, (p1, p2)->(p2.getPrice().compareTo(p1.getPrice())));
            }
        }
        else
        {
            // Ascending sorting by default
            Collections.sort(matchedProducts,
                    (product1, product2) -> product1.getPrice().compareTo(product2.getPrice()));

        }
        return matchedProducts;
    }

    //    add new Product
    public Product addProduct(Product newProduct)
    {
        products.add(newProduct);
        return newProduct;
    }

//    deactivate product
    public Boolean deactivateProduct(Long id)
    {
        List<Product> matchedProducts = new ArrayList<Product>();
        for (Product p: products) {
            if(p.getId().equals(id))
            {
                p.setActive(0);
                return true;
            }
        }
        return false;
    }

//    delete product with given id
    public boolean deleteProduct(Long id)
    {
        for (Product p: products) {
            if(p.getId().equals(id))
            {
                products.remove(p);
                return true;
            }
        }
        return false;
    }

}
