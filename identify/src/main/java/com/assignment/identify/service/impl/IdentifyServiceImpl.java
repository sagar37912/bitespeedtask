package com.assignment.identify.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.identify.entities.UserData;
import com.assignment.identify.repositories.UserDataRepository;
import com.assignment.identify.service.IdentifyService;
import com.assignment.identify.vo.RequestVO;
import com.assignment.identify.vo.ResponseVO;

@Service
public class IdentifyServiceImpl implements IdentifyService {

    @Autowired
	UserDataRepository userDataRepository;

    private static final Logger LOG = LoggerFactory.getLogger(IdentifyServiceImpl.class);

    @Override
    public ResponseVO identifyUser(RequestVO request) {
        long primId = 0;
        Set<String> email = new HashSet<>();
        Set<String> phoneNumber = new HashSet<>();
        Set<Long> secId = new HashSet<>();
        
        Optional<UserData> users = userDataRepository.findByEmailOrPhoneNumber(request.getEmail(), request.getPhoneNumber());
        List<UserData> userPrime = users.isPresent()
                        ? Collections.singletonList(users.get())
                            .stream()
                            .filter(user -> "primary".equals(user.getLinkPrecedence()))
                            .collect(Collectors.toList())
                        : Collections.emptyList();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSX");
        Date created =null;
        try {
                created = sdf.parse(sdf.format(new Date()));
            } catch (ParseException e) {
                LOG.error("Exception Occured, Date Pattern Incorrect :", e);
                throw new RuntimeException(e);
            }       
        if(users.isPresent()&&userPrime!=null) {

            if(userPrime.size()==1) {

                UserData newUser = new UserData();
                newUser.setEmail(request.getEmail());
                newUser.setPhoneNumber(request.getPhoneNumber());
                newUser.setLinkedId(userPrime.get(0).getId());
                newUser.setLinkPrecedence("secondary");
                newUser.setCreatedAt(created);
                newUser.setUpdatedAt(created);
                newUser.setDeletedAt(null);
                UserData savedUser = userDataRepository.save(newUser);
                primId = userPrime.get(0).getId();
                users.stream().map(UserData::getEmail).forEach(email::add);
                email.add(savedUser.getEmail());
                users.stream().map(UserData::getPhoneNumber).forEach(phoneNumber::add);
                phoneNumber.add(savedUser.getPhoneNumber());
            } else {
                UserData data = userPrime.get(0).getId()<userPrime.get(1).getId()?userPrime.get(0):userPrime.get(1);
                data.setLinkPrecedence("secondary");
                data.setUpdatedAt(created);
                userDataRepository.save(data);
                }

        } else {
            UserData newUser = new UserData();
            newUser.setEmail(request.getEmail());
            newUser.setPhoneNumber(request.getPhoneNumber());
            newUser.setLinkedId(null);
            newUser.setLinkPrecedence("primary");
            newUser.setCreatedAt(created);
            newUser.setUpdatedAt(created);
            newUser.setDeletedAt(null);
            UserData savedUser = userDataRepository.save(newUser);
            primId = savedUser.getId();
            email.add(savedUser.getEmail());
            phoneNumber.add(savedUser.getPhoneNumber());

        }

        ResponseVO contactVO = new ResponseVO();
        ResponseVO.Contact contact = new ResponseVO.Contact();
        contact.setPrimaryContactId(primId);
        contact.setEmails(email.toArray(new String[0]));
        contact.setPhoneNumbers(phoneNumber.toArray(new String[0]));
        contact.setSecondaryContactIds(secId.stream().toArray(Long[]::new));
        contactVO.setContact(contact);
        return contactVO;
    }

}