    package com.ticketer.ticketing.service;

    import jakarta.mail.MessagingException;
    import jakarta.mail.internet.MimeMessage;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.log4j.Log4j2;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.mail.javamail.JavaMailSender;
    import org.springframework.mail.javamail.MimeMessageHelper;
    import org.springframework.stereotype.Service;

    @RequiredArgsConstructor
    @Service
    @Log4j2
    public class EmailService {

        private final JavaMailSender sender;
        @Value("${spring.mail.username}")
        private String from;    //보내는 사람 이메일 주소

        /**
         * 신규 회원 인증 메일 발송
         * @param email 인증번호를 보낼 회원 이메일
         * @param code  인증번호
         */
        //메서드명 수정
        public void sendEmailVerifications(String email, String code) {
            MimeMessage msg = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg);

            try {
                //발송 메일 설정
                helper.setTo(email);                               //회원 이메일
                helper.setSubject("[Ticketer]이메일 인증번호입니다"); //제목
                helper.setText("인증번호:" + code);                 //내용
                helper.setFrom(from);                              //보내는 사람 이메일 주소

                sender.send(msg);

                log.info("인증 메일 전송 성공:{}",email);
            }catch(MessagingException e){
                log.error("인증 메일 전송 실패:{}",email);
                throw new RuntimeException(e);
            }

        }

    }
