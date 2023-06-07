/*
package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Book;
import com.l2i_e_commerce.model.TVA;
import com.l2i_e_commerce.dao.TVARepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class TVAServiceImpl implements TVAService {

    private final TVARepository tvaRepository;

    @Autowired
    public TVAServiceImpl(TVARepository tvaRepository) {
        this.tvaRepository = tvaRepository;
    }

    @Override
    public TVA save(TVA tva) {
        return tvaRepository.save(tva);
    }

    @Override
    public TVA update(TVA tva) {
        return tvaRepository.save(tva);
    }

    @Override
    public void delete(TVA tva) {
        tvaRepository.delete(tva);
    }

    @Override
    public void deleteById(Long id) {
        tvaRepository.deleteById(id);
    }

    @Override
    public TVA findById(Long id) {
        return tvaRepository.findById(id).orElse(null);
    }


}
*/
