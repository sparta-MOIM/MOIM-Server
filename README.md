# 🐧 대규모 모임 관리 시스템 - MOIM
> 대규모 모임의 인원관리, 일정조율, 소통을 효율적으로 도와주는 MSA 기반 모임 통합 관리 서비스</br>
> 개발 기간 : 2025.04 ~ 2025.05
<br/>
<p >
<img width="700" alt="서비스 소개" src="https://github.com/user-attachments/assets/071baaf0-2ee8-49f2-9c7c-82114b360200">
</p>
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

## ✨ Getting Started (서비스 URL 및 API 명세서)

- 서비스 URL : https://moim.agong.store
- API : (wiki에 문서화할 예정입니다.)

<br/>

## 📚 About Project
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


## 트러블 슈팅

- wiki 문서화 예정


<br/>
<br/>


## 구현/로직/리팩토링에 대한 고민

**구현에 대한 고민**

- wiki 문서화 예정

- wiki 문서화 예정


**로직에 대한 고민**

- wiki 문서화 예정
  

**리팩토링에 대한 고민**

- wiki 문서화 예정



<br/>
<br/>

# Key Features (주요 기능)
- **회원**:
  - 내용

- **세션**:
  - 내용
  
- **알림**:
  - 내용

- **채팅**:
  - ws-stomp를 통해 **실시간 채팅**이 가능합니다. (1대1 채팅과 1대N 채팅이 가능합니다)
  - **Kafka를 채팅 브로커로 사용**하여, **대용량 채팅 트래픽을 견딜 수 있도록 설계**되었습니다.
  - 기획상으로, 모임 1개당 채팅방을 1개만 생성할 수 있도록 설계되었지만, **추후 확장이 가능**합니다.

- **모임**:
  - 내용
 
- **소모임**:
  - 내용
  
- **게시글**
  - 내용
    
- **댓글**
  - 게시글과 댓글 도메인은 분리되어 있습니다.
  - 부모댓글과 자식댓글을 구분하여 댓글과 대댓글을 구분지었습니다.
  - 게시된지 시간이 지난 게시글의 댓글은 수정이 빈번하게 일어나지 않는다고 판단하여, 댓글 조회 최적화를 위해 캐싱을 도입하였습니다.
  - 게시글이 삭제되면, 댓글도 모두 삭제되는 트랜잭션을 갖고 있습니다.
 
- **일정**
  - 내용

<br/>
<br/>


## ERD

- 링크

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

- wiki로 문서화할 예정입니다.

