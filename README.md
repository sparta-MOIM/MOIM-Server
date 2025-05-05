# 대규모 모임 관리 시스템 - MOIM

<img src="https://github.com/user-attachments/assets/071baaf0-2ee8-49f2-9c7c-82114b360200" alt="서비스 소개" width="100%"/>

<br/>
<br/>

# Members (팀원 및 팀 소개)
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

# Getting Started (서비스 URL 및 API 명세서)

- 서비스 URL : https://moim.agong.store
- API : (wiki에 문서화할 예정입니다.)

<br/>
<br/>

# About Project
- **프로젝트 목적**: 대규모 모임 관리를 보다 쉽고 편하게 도와주는 서비스 입니다.
- **프로젝트 설명**: 모든 인원이 공지를 확인할 수 있도록 주기적으로 알람을 보내주고, 선착순 세션, 채팅, 투표 등의 부가적인 기능을 통해 모임을 관리를 수월하게 할 수 있도록 지원해줍니다.
- **프로젝트 구성** : 회원, 세션, 알림, 채팅, 모임, 소모임, 게시글, 댓글, 일정 도메인으로 이루어져 있으며, 서비스 간의 통신은 FeignClient로 이루어집니다. 또한 Kafka를 사용하여 이벤트 기반 아키텍처를 구성하였으며, OpenTelemetry를 통하여 모든 로그들을 효율적으로 수집하였으며, 분산추적을 수행하였고, Grafana로 시각화를 진행하여 지속적인 모니터링을 진행했습니다.
- **아키텍쳐 설명** : MSA 기반이며, 각 서비스 인스턴스들은 AWS ECS 에서 동작합니다. 젠킨스를 통한 CI/CD 파이프라인을 구축하였습니다. RDB는 MySQL을 사용하였고, AWS RDS에 DB서버를 구축하였습니다. NoSQL인 Redis와 MongoDB는 AWS EC2에 구축하였습니다. Kafka/Zookeeper, Prometheus, Grafana, Grafana Tempo, Grafana Loki, OpenTelemetry는 AWS EC2에 배포환경을 구축하였습니다.
<br/>

![image](https://github.com/user-attachments/assets/73b40880-9225-4ceb-ad65-7410f13e0f2c)


<br/>
<br/>


## 해당 스택을 적용한 이유

### Redis

- 내용 입력 예정

### QueryDSL

- 내용 입력 예정

### Swagger

- 내용 입력 예정

### OpenTelemetry, Loki, Tempo, Prometheus, Grafana

- 내용 입력 예정

### Kafka

- 내용 입력 예정

### SonarCube

- 내용 입력 예정

### STOMP

- 내용 입력 예정


<br/>
<br/>


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
  - 내용

- **모임**:
  - 내용
 
- **소모임**:
  - 내용
  
- **게시글**
  - 내용
    
- **댓글**
  - 내용
 
- **일정**
  - 내용

<br/>
<br/>


# ERD
<br/>
<br/>

<img width="443" alt="image" src="https://github.com/user-attachments/assets/cbd62bc6-8e74-4d89-a434-91bb57df744f" />
<img width="501" alt="image" src="https://github.com/user-attachments/assets/00199941-d48c-441b-85b3-9ac77e1f6d86" />
<img width="861" alt="image" src="https://github.com/user-attachments/assets/da75a30a-0247-4986-9228-a23e13fa7c69" />
<img width="772" alt="image" src="https://github.com/user-attachments/assets/90a8bd97-3e85-4f3a-9f25-a68ea54246bf" />
<img width="431" alt="image" src="https://github.com/user-attachments/assets/54feef4e-c300-49a5-9a9d-041e15c5a63d" />
<img width="497" alt="image" src="https://github.com/user-attachments/assets/eef50f82-c287-4f3f-af65-09cf83f3158e" />


 <br/>


<br/>
<br/>


# Technology Stack (기술 스택)

- 업데이트 예정입니다.

## BackEnd
|  |  |  |
|-----------------|-----------------|-----------------|
| SpringBoot  |  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white" alt="SpringBoot" width="200"> | 3.4.3    |
| Java  |  <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white" alt="Java" width="200" > | 17 |
| Spring Data JPA  |  <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=SpringDataJPA&logoColor=white" alt="JPA" width="200" >    | 5.0.0  |
| QueryDSL  |  <img src="https://img.shields.io/badge/QueryDSL-0769AD?style=for-the-badge&logo=QueryDSL&logoColor=white" alt="QueryDSL" alt="QueryDSL" width="200" >    | 1.11.12    |
| Spring Security |  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white" alt="QueryDSL" alt="QueryDSL" width="200">    | 3.4.2    |

<br/>

## Infra
|  |  |  |
|-----------------|-----------------|-----------------|
| PostgreSQL  |  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=PostgreSQL&logoColor=white" alt="PostgreSQL" width="200">    | 16.3 |
| pgAdmin    |  <img src="https://img.shields.io/badge/pgAdmin-4169E1?style=for-the-badge&logo=pgAdmin&logoColor=white" alt="pgAdmin" width="200">    | latest  |
| Docker  |  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white" alt="docker" width="200">    |  |

<br/>
<br/>

# Development Convention (개발 컨벤션)

- wiki로 문서화할 예정입니다.

<br/>
<br/>

# Development Workflow (개발 워크플로우)
## 브랜치 전략 (Branch Strategy)
우리의 브랜치 전략은 아래와 같은 Git Flow를 기반으로 하며, 다음과 같은 브랜치를 사용합니다. </br>
MSA 구조로 인하여, 각 서비스별로 브랜치를 만들었고, 브랜치 별로 CI/CD 작업을 통해 서비스 별 배포를 진행하였습니다. (즉, 각 브랜치는 아래와 같은 깃 플로우를 지킵니다.)

<img src="https://github.com/user-attachments/assets/f683ef08-c485-4447-a1d8-a5c6468e093a" alt="git flow" width="500">


- Main Branch
  - 배포 가능한 상태의 코드를 유지합니다.
  - 모든 배포는 이 브랜치에서 이루어집니다.
  
- develop Branch
  - 통합 기능 관리 브랜치 입니다
  - feat에서 개발한 기능을 develop 브랜치에서 통합하여 관리합니다.
 
- feat Branch
  - 기능 개발 브랜치 입니다.
  - 기능 단위로 브랜치를 나누어 기능을 개발하였습니다.
 
- refactor Branch
  - 코드 리팩토링 브랜치 입니다.
  - 코드 리팩토링이 필요한 경우 refactor 브랜치에서 작업했습니다.
 
- release Branch
  - 배포 전 버전을 관리하는 브랜치 입니다.
  - 최종 배포하기 전 테스트를 진행하고, 이상이 없다면 Main브랜치로 배포를 진행합니다.
 
- hotfix Branch
  - 핫픽스를 관리하는 브랜치 입니다.
  - 배포된 환경에서 수정사항이 발생했을 경우, hotfix 브랜치에서 관리하였습니다.

<br/>
<br/>

