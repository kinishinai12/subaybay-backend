package com.kinishinai.contacttracingapp.repository;

import com.kinishinai.contacttracingapp.model.ScannedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScannedUserRepository extends PagingAndSortingRepository<ScannedUser, Long> {

//    @Query(value="select * from ScannedUser where firstname like %?1%", nativeQuery = true)
//    Page<ScannedUser> findByFirstname(String name, Pageable pageable);
}

//@Repository
//public interface ProductJpaRepository extends PagingAndSortingRepository<Products, UUID>{
////	List<Products> findAllByCategory(String category);
//
//    @Query(value="select * from Products where product_name like %?1%", nativeQuery = true)
//    Page<Products> findByProductName(String productName, Pageable pageable);
//    Slice<Products> findAllByCategory (String category, Pageable pageable);
//    Page<Products> findProductByCategory(String category, Pageable pageable);
//}
