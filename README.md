# 🐧 대규모 모임 관리 시스템 - MOIM
> 대규모 모임의 인원관리, 일정조율, 소통을 효율적으로 도와주는 MSA 기반 모임 통합 관리 서비스</br>
> 개발 기간 : 2025.04 ~ 2025.05
<br/>
<p>
<img width="700" alt="서비스 소개" src="https://github.com/user-attachments/assets/071baaf0-2ee8-49f2-9c7c-82114b360200">
</p>
<br/>
<br/>

## 🎯 Members (팀원 및 팀 소개)
<table style="margin-left:auto;margin-right:auto;">
  <tr height="160px">
    <th align="center" width="140px">
      <a href="https://github.com/kim0527"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/143387515?v=4"/></a>
    </th>
    <th align="center" width="140px">
      <a href="https://github.com/asqwklop12"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/33236685?v=4"/></a>
    </th>
    <th align="center" width="140px">
      <a href="https://github.com/cchoijjinyoung"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/68311264?v=4"/></a>
    </th>
    <th align="center" width="140px">
      <a href="https://github.com/kim946509"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/58037317?v=4"/></a>
    </th>
   <th align="center" width="140px">
      <a href="https://github.com/High-Quality-Coffee"><img height="130px" width="130px" src="https://avatars.githubusercontent.com/u/125748258?v=4"/></a>
    </th>
  </tr>
  <tr>
    <td align="center" width="160px">
      <a href="https://github.com/kim0527"><strong>김형주</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/asqwklop12"><strong>박용훈</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/cchoijjinyoung"><strong>최진영</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/kim946509"><strong>김대연</strong></a>
    </td>
    <td align="center" width="160px">
      <a href="https://github.com/High-Quality-Coffee"><strong>박규원</strong></a>
    </td>
  </tr>
  <tr>
    <td align="center" width="160px">
      게시글, 일정
    </td>
    <td align="center" width="160px">
      소모임, 세션
    </td>
    <td align="center" width="160px">
      회원
    </td>
    <td align="center" width="160px">
      알림, 모임
    </td>
    <td align="center" width="160px">
      채팅, 댓글
    </td>
  </tr>
  <tr>
    <td align="center" width="160px">
       Lead
    </td>
    <td align="center" width="160px">
       Tech Lead
    </td>
    <td align="center" width="160px">
       BE
    </td>
    <td align="center" width="160px">
       BE
    </td>
    <td align="center" width="160px">
       BE
    </td>
  </tr>
</table>

<br/>
<br/>

## ✨ Getting Started (서비스 URL 및 API 명세서)

- 서비스 URL : https://moim.agong.store
- API : (wiki에 문서화할 예정입니다.)

<br/>
<br/>

## 🧑‍💻 About Project
> 🧩 서버 아키텍처
> 
<p align="center">
<img width="1000" alt="서비스 소개" src="https://github.com/user-attachments/assets/73b40880-9225-4ceb-ad65-7410f13e0f2c">
</p>
<br/>

**프로젝트 목적**
  - 대규모 모임 관리를 보다 쉽고 편하게 도와주는 서비스 입니다.
<br/>

**프로젝트 설명**
  - 모든 인원이 공지를 확인할 수 있도록 주기적으로 알람을 보내주고, 선착순 세션, 채팅, 투표 등의 부가적인 기능을 통해 모임을 관리를 수월하게 할 수 있도록 지원해줍니다.
<br/>

**프로젝트 구성**
  - 회원, 세션, 알림, 채팅, 모임, 소모임, 게시글, 댓글, 일정 도메인으로 이루어져 있으며, 서비스 간의 통신은 FeignClient로 이루어집니다. 또한 Kafka를 사용하여 이벤트 기반 아키텍처를 구성하였으며, OpenTelemetry를 통하여 모든 로그들을 효율적으로 수집하였으며, 분산추적을 수행하였고, Grafana로 시각화를 진행하여 지속적인 모니터링을 진행했습니다.
<br/>

**아키텍쳐 설명**
  - MSA 기반이며, 각 서비스 인스턴스들은 AWS ECS 에서 동작합니다. 젠킨스를 통한 CI/CD 파이프라인을 구축하였습니다. RDB는 MySQL을 사용하였고, AWS RDS에 DB서버를 구축하였습니다. NoSQL인 Redis와 MongoDB는 AWS EC2에 구축하였습니다. Kafka/Zookeeper, Prometheus, Grafana, Grafana Tempo, Grafana Loki, OpenTelemetry는 AWS EC2에 배포환경을 구축하였습니다.

<br/>
<br/>

## 🔑 Key Features (주요 기능)
- **회원**:
  - 회원가입이 가능합니다.username은 유니크하며, password는 인코딩되어 저장됩니다.
  - 로그인/로그아웃이 가능합니다. 로그인 시 JWT를 쿠키로 발급하고, 로그아웃 시 쿠키를 만료시킵니다.
  - 게이트웨이로부터 인증 요청이 들어오면, 검증을 수행한 뒤 Passport를 발급합니다. Passport는 각 서비스에서 현재 요청의 유저 정보로 활용됩니다.
  - 회원 조회, 수정, 삭제가 가능합니다.

- **세션**:
  - 세션 생성/수정이 가능합니다.
  - 세션에 참여/나가기가 가능하도록 설계되었습니다.
  - 세션 생성을 하게 되면 모임 - 매니저가 승인을 시켜 세션을 활성화가 가능합니다.
  - 다수의 사용자가 동시에 입장할 수 있도록 루아스크립트를 통해 원자성을 제어하고 있습니다.
  
- **알림**:
  - 다양한 서비스로부터 발생한 이벤트를 수신하여 알림을 생성합니다. 예를 들어, 모임 가입 신청, 승인 등의 이벤트를 처리합니다.
  - 이벤트 타입에 따라 사전에 정의된 알림 템플릿을 사용하여 사용자에게 전달할 알림 메시지를 자동으로 생성합니다.
  - 생성된 알림은 DB에 저장되며, 각 알림은 읽음/안읽음 상태를 통해 구분됩니다. 이를 기반으로 필터링된 알림 조회가 가능합니다.
  - 다양한 알림 유형이 전략 패턴 기반으로 구현되어 있어 새로운 알림 타입이 쉽게 확장 가능합니다.

- **채팅**:
  - ws-stomp를 통해 **실시간 채팅**이 가능합니다. (1대1 채팅과 1대N 채팅이 가능합니다)
  - **Kafka를 채팅 브로커로 사용**하여, **대용량 채팅 트래픽을 견딜 수 있도록 설계**되었습니다.
  - 기획상으로, 모임 1개당 채팅방을 1개만 생성할 수 있도록 설계되었지만, **추후 확장이 가능**합니다.

- **모임**:
  - 사용자는 새로운 모임을 생성할 수 있으며, 모임 이름, 설명, 모집 조건 등 다양한 정보를 입력하여 개설할 수 있습니다.
  - 사용자는 관심 있는 모임에 신청할 수 있고, 관리자는 신청자를 승인 또는 거절할 수 있습니다. 이 과정은 알림 시스템과 연동됩니다.
  - 관리자는 모임에 가입된 멤버들의 Role을 부여하거나, 멤버를 추가/삭제할 수 있습니다. Role에 따라 접근 권한이 구분됩니다. 
  - 모임 멤버 정보를 캐시(Redis)에 저장하여 조회 성능을 향상시켰습니다. 자주 조회되는 멤버 정보 요청에 빠른 응답이 가능합니다.
- **소모임**:
  - 소모임 생성/수정이 가능합니다.
  - 소모임에 참여/나가기가 가능하도록 설계되었습니다.
  
- **게시글**
  - 모임별 게시글 관리가 가능하며, 게시글은 피드 혹은 투표로 생성할 수 있습니다.
  - 권한에 따라 게시글 수정 및 삭제가 가능합니다.
  - 조회가 빈번할 것으로 판단하여, CQRS를 활용하여 읽기 DB(MongoDB)를 분리함으로써 조회 성능을 높였습니다.
  - DB 사이의 데이터 일관성은 이벤트 소싱으로 구성했으며, 정확히 한번을 보장하기 위해 OutBox패턴과 멱등적 소비자 패턴을 활용하였습니다.
  - 캐시와 인덱스를 활용하여 조회 성능을 더욱 최적화 가능합니다.
    
- **댓글**
  - 게시글과 댓글 도메인은 분리되어 있습니다.
  - 부모댓글과 자식댓글을 구분하여 댓글과 대댓글을 구분지었습니다.
  - 게시된지 시간이 지난 게시글의 댓글은 수정이 빈번하게 일어나지 않는다고 판단하여, 댓글 조회 최적화를 위해 캐싱을 도입하였습니다.
  - 게시글이 삭제되면, 댓글도 모두 삭제되는 트랜잭션을 갖고 있습니다.
 
- **일정**
  - 모임별 일정 관리가 가능합니다.
  - 모임의 권한에 따라 일정을 수정하고, 삭제할 수 있습니다.

<br/>
<br/>


## 📚 트러블 슈팅

- [🚧 알림 서비스 잦은 INSERT로 인한 DB 부하](https://github.com/sparta-MOIM/MOIM-Server/wiki/%F0%9F%9A%A7-%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85-%5B%EC%95%8C%EB%A6%BC-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%9E%A6%EC%9D%80-INSERT%EB%A1%9C-%EC%9D%B8%ED%95%9C-DB-%EB%B6%80%ED%95%98%5D)
- [🚧 taggedIds 변경시, 발생하는 쿼리 수 최적화](https://github.com/sparta-MOIM/MOIM-Server/wiki/%F0%9F%9A%A7-%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85-%5BtaggedIds-%EB%B3%80%EA%B2%BD%EC%8B%9C,-%EB%B0%9C%EC%83%9D%ED%95%98%EB%8A%94-%EC%BF%BC%EB%A6%AC-%EC%88%98-%EC%B5%9C%EC%A0%81%ED%99%94%5D)
 

<br/>
<br/>


## 💬 개발 과정에서의 고민 (구현/로직/리팩토링)

- [ 🤔 대기큐 처리 속도 개선 ](https://github.com/sparta-MOIM/MOIM-Server/wiki/%F0%9F%A4%94-%EA%B5%AC%ED%98%84-%EB%A1%9C%EC%A7%81-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81-%EA%B3%A0%EB%AF%BC-%5B%EB%8C%80%EA%B8%B0%ED%81%90-%EC%B2%98%EB%A6%AC-%EC%86%8D%EB%8F%84-%EA%B0%9C%EC%84%A0-%5D)
- [ 🤔 Redisson 기반 세션 참가 기능 최적화 과정 ](https://github.com/sparta-MOIM/MOIM-Server/wiki/%F0%9F%A4%94-%EA%B5%AC%ED%98%84-%EB%A1%9C%EC%A7%81-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81-%EA%B3%A0%EB%AF%BC:-%5BRedisson-%EA%B8%B0%EB%B0%98-%EC%84%B8%EC%85%98-%EC%B0%B8%EA%B0%80-%EA%B8%B0%EB%8A%A5-%EC%B5%9C%EC%A0%81%ED%99%94-%EA%B3%BC%EC%A0%95%5D)
- [ 🤔 쿼리튜닝 ‐ 쓰기 최적화 ](https://github.com/sparta-MOIM/MOIM-Server/wiki/%F0%9F%A4%94-%EA%B5%AC%ED%98%84-%EB%A1%9C%EC%A7%81-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81-%EA%B3%A0%EB%AF%BC:-%5B%EC%BF%BC%EB%A6%AC%ED%8A%9C%EB%8B%9D-%E2%80%90-%EC%93%B0%EA%B8%B0-%EC%B5%9C%EC%A0%81%ED%99%94%5D)
- [ 🤔 관측 환경 구축 Part#1 ](https://github.com/sparta-MOIM/MOIM-Server/wiki/%F0%9F%A4%94-%EA%B5%AC%ED%98%84-%EB%A1%9C%EC%A7%81-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81-%EA%B3%A0%EB%AF%BC:-%5B%EA%B4%80%EC%B8%A1-%ED%99%98%EA%B2%BD-%EA%B5%AC%EC%B6%95-Part%231%5D)
- [ 🤔 관측 환경 구축 Part#2 ](https://github.com/sparta-MOIM/MOIM-Server/wiki/%EA%B5%AC%ED%98%84-%EB%A1%9C%EC%A7%81-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81-%EA%B3%A0%EB%AF%BC:-%5B%EA%B4%80%EC%B8%A1-%ED%99%98%EA%B2%BD-%EA%B5%AC%EC%B6%95-Part%232%5D)


<br/>
<br/>


## 📊 ERD

> [🔗 ERDCloud Link](https://www.erdcloud.com/p/iDACkZjnWaotukz3t)

<p >
<img width="900" alt="erd" src="https://github.com/user-attachments/assets/aa859830-9d14-4fda-b235-be19b91270c2">
</p>

</br>

## 📝 Technology Stack (기술 스택)

<h3>Framework</h3>
<br/>
<p>
  <img src="https://img.shields.io/badge/OpenJDK-v17.0.14-000000?style=flat&logo=OpenJDK&logoColor=white" alt="openJDK"> <img src="https://img.shields.io/badge/Spring Boot-v3.4.4-6DB33F?style=flat&logo=SpringBoot&logoColor=white" alt="Spring Boot"> <img src="https://img.shields.io/badge/Spring Data JPA-v3.4.4-6DB33F?style=flat&logo=Spring&logoColor=white" alt="Spring"> 
  <br/> <img src="https://img.shields.io/badge/Spring Security-v6.2.5-6DB33F?style=flat&logo=springsecurity&logoColor=white" alt="Spring Security"> <img src="https://img.shields.io/badge/Spring Cloud-v2024.0.0-6DB33F?style=flat&logo=Spring&logoColor=white" alt="Spring Cloud"> <img src="https://img.shields.io/badge/QueryDsl-v5.0.0-0769AD?style=flat&logo=Github&logoColor=white" alt="QueryDsl"> 
</p>

<br/>
<h3>Database</h3>
<br/>
<p>
  <img src="https://img.shields.io/badge/MySQL-v.latest-4479A1?style=flat&logo=MySQL&logoColor=white" alt="MySQL"> <img src="https://img.shields.io/badge/Redis-v.latest-FF4438?style=flat&logo=Redis&logoColor=white" alt="Redis"> <img src="https://img.shields.io/badge/Redisson-v.latest-FF4438?style=flat&logo=Redis&logoColor=white" alt="Redisson">
  <br/> <img src="https://img.shields.io/badge/Lua Script-v.latest-2C2D72?style=flat&logo=Lua&logoColor=white" alt="Lua"> <img src="https://img.shields.io/badge/MongoDB-v.latest-47A248?style=flat&logo=mongodb&logoColor=white" alt="mongodb"> <br/> <img src="https://img.shields.io/badge/phpMyAdmin-v.latest-6C78AF?style=flat&logo=phpMyAdmin&logoColor=white" alt="phpMyAdmin">
  <img src="https://img.shields.io/badge/RedisInsight-v.latest-FF4438?style=flat&logo=Redis&logoColor=white" alt="RedisInsight"> <img src="https://img.shields.io/badge/MongoExpress-v.latest-47A248?style=flat&logo=mongodb&logoColor=white" alt="mongo-express">
</p>

<br/>
<h3 >Monitoring</h3>
<br/>
<p>
  <img src="https://img.shields.io/badge/Grafana-v.latest-F46800?style=flat&logo=Grafana&logoColor=white" alt="Grafana"> <img src="https://img.shields.io/badge/Prometheus-v.latest-E6522C?style=flat&logo=prometheus&logoColor=white" alt="prometheus"> <img src="https://img.shields.io/badge/Grafana Tempo-v.latest-F46800?style=flat&logo=Grafana&logoColor=white" alt="Grafana Tempo">
  <br/> <img src="https://img.shields.io/badge/Grafana Loki-v.latest-F46800?style=flat&logo=Grafana&logoColor=white" alt="Grafana Loki"> <img src="https://img.shields.io/badge/OpenTelemetry-v.latest-FFE033?style=flat&logo=opentelemetry&logoColor=white" alt="opentelemetry">
</p>

<br/>
<h3>Infra</h3>
<br/>
<p>
  <img src="https://img.shields.io/badge/Docker-v.latest-2496ED?style=flat&logo=docker&logoColor=white" alt="docker"> <img src="https://img.shields.io/badge/Docker Compose-v.latest-004B8D?style=flat&logo=docker&logoColor=white" alt="docker compose"> <img src="https://img.shields.io/badge/Apache Kafka-v.latest-A100FF?style=flat&logo=apachekafka&logoColor=white" alt="apachekafka"> <img src="https://img.shields.io/badge/Jenkins-v.latest-D24939?style=flat&logo=jenkins&logoColor=white" alt="jenkins">
   <br/> <img src="https://img.shields.io/badge/EC2-None-FF9900?style=flat&logo=amazonec2&logoColor=white" alt="amazonec2"> <img src="https://img.shields.io/badge/ECS-None-FF9900?style=flat&logo=amazonecs&logoColor=white" alt="amazonecs"> <img src="https://img.shields.io/badge/AWS Fargate-None-FF9900?style=flat&logo=awsfargate&logoColor=white" alt="awsfargate"> <img src="https://img.shields.io/badge/ECR-None-FF9900?style=flat&logo=amazonwebservices&logoColor=white" alt="amazonwebservices">
  <br/> <img src="https://img.shields.io/badge/RDS-None-527FFF?style=flat&logo=amazonrds&logoColor=white" alt="amazonrds"> <img src="https://img.shields.io/badge/Route 53-None-8C4FFF?style=flat&logo=amazonroute53&logoColor=white" alt="amazonroute53"> <img src="https://img.shields.io/badge/Nginx-None-009639?style=flat&logo=nginx&logoColor=white" alt="nginx"> <img src="https://img.shields.io/badge/Nginx Proxy Manager-None-F15833?style=flat&logo=nginxproxymanager&logoColor=white" alt="nginxproxymanager">
</p>

</br>

## 🎲 Development Convention (개발 컨벤션)

- [🚀 개발 컨벤션](https://github.com/sparta-MOIM/MOIM-Server/wiki/Development-Workflow-(%EA%B0%9C%EB%B0%9C-%EC%9B%8C%ED%81%AC%ED%94%8C%EB%A1%9C%EC%9A%B0))

<br/>

## 📌 Development Workflow (개발 워크플로우)

- [🚀 Development Workflow (개발 워크플로우)](https://github.com/sparta-MOIM/MOIM-Server/wiki/%F0%9F%9A%80-Development-Workflow-(%EA%B0%9C%EB%B0%9C-%EC%9B%8C%ED%81%AC%ED%94%8C%EB%A1%9C%EC%9A%B0))

