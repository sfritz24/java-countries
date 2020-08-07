package com.lambdaschool.javacountries.controllers;

import com.lambdaschool.javacountries.models.Country;
import com.lambdaschool.javacountries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{
    @Autowired
    CountryRepository conrepos;

    private List<Country> findCountries(List<Country> myList, CheckCountry tester)
    {
        List<Country> tempList = new ArrayList<>();

        for (Country c : myList)
        {
            if (tester.test(c))
            {
                tempList.add(c);
            }
        }

        return tempList;
    }

    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries()
    {
        List<Country> myList = new ArrayList<>();
        conrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listAllByName(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();
        conrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        List<Country> listAllByName = findCountries(myList, c -> c.getName().toLowerCase().charAt(0) == Character.toLowerCase(letter));
        return new ResponseEntity<>(listAllByName, HttpStatus.OK);
    }

    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> totalPopulation()
    {
        List<Country> myList = new ArrayList<>();
        conrepos.findAll().iterator().forEachRemaining(myList::add);
        long sumPop = 0;
        for (Country c : myList)
        {
            sumPop += c.getPopulation();
        }
        System.out.println("The total population is: " + sumPop);
        return new ResponseEntity<>("System Ok", HttpStatus.OK);
    }

    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> minPopulation()
    {
        List<Country> myList = new ArrayList<>();
        conrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> Long.compare(c1.getPopulation(), c2.getPopulation()));
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);
    }

    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> maxPopulation()
    {
        List<Country> myList = new ArrayList<>();
        conrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> Long.compare(c2.getPopulation(), c1.getPopulation()));
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);
    }
}
