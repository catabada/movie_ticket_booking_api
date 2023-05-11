package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.product.ProductCreate;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.product.ProductUpdate;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.product.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/search")
    public ResponseEntity<HttpResponse> getProducts() {
        return ResponseEntity.ok().body(HttpResponseSuccess.success(productService.getAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getProduct(final @PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(HttpResponseSuccess.success(productService.getByProduct(id)).build());
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createProduct(@RequestBody final ProductCreate create) throws Exception {
        return ResponseEntity.ok().body(HttpResponseSuccess.success(productService.createProduct(create.getProduct())).build());
    }

    @PutMapping("/update")
    public ResponseEntity<HttpResponse> updateProduct(@RequestBody final ProductUpdate update) throws Exception {
        return ResponseEntity.ok().body(HttpResponseSuccess.success(productService.updateProduct(update)).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteProduct(@PathVariable final Long id) throws Exception {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body(HttpResponseSuccess.success().build());
    }

}
