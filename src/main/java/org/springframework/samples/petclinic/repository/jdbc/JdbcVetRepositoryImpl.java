/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Repository;

/**
 * A simple JDBC-based implementation of the {@link VetRepository} interface.
 * Uses @Cacheable to cache the result of the {@link findAll} method
 * 
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Sam Brannen
 * @author Thomas Risberg
 * @author Mark Fisher
 * @author Michael Isvy
 */
@Repository
public class JdbcVetRepositoryImpl implements VetRepository {

   private JdbcTemplate jdbcTemplate;

   private SimpleJdbcInsert insertVet;

   @Autowired
   public JdbcVetRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
      this.insertVet = new SimpleJdbcInsert(dataSource).withTableName("vets").usingGeneratedKeyColumns("id");

      this.jdbcTemplate = jdbcTemplate;
   }

   /**
    * Refresh the cache of Vets that the ClinicService is holding.
    * 
    * @see org.springframework.samples.petclinic.model.service.ClinicService#findVets()
    */
   @Override
   public Collection<Vet> findAll() throws DataAccessException {
      List<Vet> vets = new ArrayList<Vet>();
      // Retrieve the list of all vets.
      vets.addAll(this.jdbcTemplate.query("SELECT id, first_name, last_name FROM vets ORDER BY last_name,first_name",
            ParameterizedBeanPropertyRowMapper.newInstance(Vet.class)));

      // Retrieve the list of all possible specialties.
      final List<Specialty> specialties = this.jdbcTemplate.query("SELECT id, name FROM specialties",
            ParameterizedBeanPropertyRowMapper.newInstance(Specialty.class));

      // Build each vet's list of specialties.
      for (Vet vet : vets) {
         Map<String, Object> params = new HashMap<String, Object>();
         params.put("id", vet.getId());
         final List<Integer> vetSpecialtiesIds = this.jdbcTemplate.query("SELECT specialty_id FROM vet_specialties WHERE vet_id=?",
               new ParameterizedRowMapper<Integer>() {
                  @Override
                  public Integer mapRow(ResultSet rs, int row) throws SQLException {
                     return Integer.valueOf(rs.getInt(1));
                  }
               }, vet.getId().intValue());
         for (int specialtyId : vetSpecialtiesIds) {
            Specialty specialty = EntityUtils.getById(specialties, Specialty.class, specialtyId);
            vet.addSpecialty(specialty);
         }
      }
      return vets;
   }

   @Override
   public Vet findById(int id) throws DataAccessException {
      Vet vet;
      try {
         vet = this.jdbcTemplate.queryForObject("SELECT id, first_name, last_name FROM vets WHERE id= :id",
               ParameterizedBeanPropertyRowMapper.newInstance(Vet.class), id);
      } catch (EmptyResultDataAccessException ex) {
         throw new ObjectRetrievalFailureException(Vet.class, id);
      }
      return vet;
   }

   @Override
   public void save(Vet vet) {
      // TODO Auto-generated method stub

   }

   @Override
   public int delete(Vet vet) throws DataAccessException {
      // TODO Auto-generated method stub
      return -1;
   }
}
