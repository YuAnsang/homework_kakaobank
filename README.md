# 블로그 검색 Project

## Project Description
- 해당 프로젝트 Open API를 활용한 블로그 검색 프로젝트입니다.
- 별도 첨부된 요구사항에 맞춰 블로그 검색 프로그램을 작성합니다.

## 지원자 정보
- 2126-000131_유안상_서버 개발자

## 빌드 결과물
- https://github.com/YuAnsang/20230705_2126-000131/releases/download/0.0.2/homework-0.0.2.jar

## Application Profile
- test
  - 테스트 코드가 실행되는 profile입니다.
  - 테스트 코드에서 의존 구성요소를(e.g. 외부 통신, 다른 서비스 등) 사용할 수 없을 때는 **테스트 더블**을 사용하여 테스트합니다.
- default
  - 로컬 환경에서 실행되는 profile입니다.
  - 의존 구성요소는 docker-compose를 통해 실행합니다.
  - docker-compose로 해결 할 수 없는 경우 Profile별로 별도 로직을 수행할 수 있도록 합니다.
- 과제 프로젝트이므로 test, default Profile 외에는 지원하지 않습니다.

## 실행 가이드
- test
  - JUnit으로 구성된 테스트 케이스를 실행합니다.
  - RDB는 In-Memory DB를 사용하고, 서드파티 구동을 위해 testContainers를 사용했습니다.
  - 아래 명령어를 입력합니다.
    ```
    ./gradlew test
    ```
- default
  - 로컬 환경에서 Application을 구동합니다.
  - 각 서드 파티(Cache 등)은 **Docker에 의존성이 있습니다**.
  - 링크 걸린 jar 파일을 실행하기전 **반드시 프로젝트 root에 있는 docker-compose.yml 파일로 환경을 구성해야합니다.**
  ```
  docker-compose up -d
  ```
  - 그 후 아래 명령어로 jar 파일을 실행합니다.
  ```
  java -jar homework-0.0.2.jar
  ```
  - API Spec은 [Swagger](http://localhost:8080/api-docs)를 통해 확인 가능합니다.

## 프로젝트를 진행하며 신경 썼던점
- 크게 세가지를 신경쓰면서 진행하였습니다.
- 첫번째로는 도메인간 연관 관계 및 layer(package) 구조에 대해서 집중하였습니다.
  - 프로젝트를 진행하다보면 도메인간 연관 관계나 의존 관계를 신경쓰지 않고 개발하다가 낭패를 보는 경우가 많았습니다. (ex. CustomerService가 OrderService를 참조하고 있다거나)
  - 도메인간의 무분별한 참조를 금지하여 각 도메인 별로 비즈니스 로직에 집중하였고, 향후 각 도메인들이 커져서 별도의 서비스로 분리되어야 하는 경우까지 고려하였습니다.
  - 우대사항에 멀티 모듈 구성을 언급하셨지만, 프로젝트 볼륨이 크지않다고 판단하여 멀티 모듈로 구성하지 않았으며 ArchUnit을 통해 의존 관계를 강제하였습니다.
- 두번째로는 인기 검색어 TOP 10입니다.
  - 최초에는 "검색어 별로 검색된 횟수도 함께 표기" 및 "키워드 별로 검색된 횟수의 정확도"만을 생각하여 단순히 total count에만 집중하여 구현하였습니다.
  - 하지만 "인기" 검색어의 경우 시간의 흐름에 따라 제공하는 정보가 달라져야 한다고 생각하여 이를 반영하였습니다.
  - 특히 TIME_BASED와 COUNT_BASED 사이에서 고민하였는데, 과제 프로젝트이다 보니 TIME_BASED인 경우 기준 설정도 애매하고 테스트 시 번거로움이 있을 것을 고려하여 COUNT_BASED로 구현하였습니다.
  - 기준이 되는 slidingWindowSize는 기본 2000개로 잡아놨으며, application 설정을 통해 변경 할 수 있도록 구현했습니다.
- 세번째로는 Circuit Breaker입니다.
  - 서버간의 통신을 하다보면 언젠간 한번쯤은 장애가 발생하는 상황을 겪고는 합니다.
  - 최초에는 Retry 로직만 있으면 되겠다고 생각하였지만, Retry의 경우 maxAttempts * waitDuration의 시간 만큼 요청 Thread를 잡아 먹고, 혹시라도 대규모 트래픽으로 요청이 들어온다면 Open API의 장애와는 별개로 해당 서버도 문제가 발생할 것으로 판단하였습니다.
  - 이에 Curcuit Breaker를 도입하게 되었습니다. Priority가 높은 카카오 블로그 검색의 경우 Curcuit Breaker가 Open되면 카카오쪽으로는 요청을 하지 않고, fallback으로 네이버 검색을 요청하도록 구현하였습니다.
  - Retry의 경우 UX를 위해 간헐적으로 발생 할 수 있는 오류에 대비하여 최소한으로 설정하였습니다.

## Technologies
- 필수 요구 사항
  - Java 17
  - SpringBoot 3.1.1
  - JPA
  - H2
- 외부 라이브러리 및 오픈소스
  - Lombok
    - Getter, Setter, Constructor등 Boilerplate 코드 Reducing을 위해 사용
  - Mapstruct
    - 반복적인 데이터 변환 작업을 간소화 하기 위해 사용.
  - Retrofit2
    - HTTP Client
    - Type Safety를 위해 해당 라이브러리 사용
  - Docker & Redisson
    - 동시성 문제를 해결을 위한 Lock용으로 사용
    - Redis는 Docker-compose를 사용하여 실행합니다.
  - Testcontainers
    - 테스트케이스에서 Redis 실행을 위해 사용
  - ArchUnit
    - 패키지 및 모듈 접근 제한을 위해 사용
  - Resilience4j
    - 블로그 검색 장애 대응을 위한 circuit breaker

## Code Convention
- 기본적으로 [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) 를 준수합니다.
- 프로젝트 root에 있는 [intellij-java-google-style.xml](./intellij-java-google-style.xml)를 import하여 사용합니다.

## Package Structure
```
└── source_root
    ├── common
    │ ├── aop
    │ ├── config
    │ ├── dto
    │ │ ├── request
    │ │ └── response
    │ ├── enums
    │ ├── event
    │ └── listener
    ├── domain
    │ ├── blog
    │ │ ├── controller
    │ │ ├── dto
    │ │ ├── enums
    │ │ ├── implement
    │ │ └── service
    │ ├── trending
    │ │ ├── controller
    │ │ ├── dto
    │ │ ├── enums
    │ │ ├── implement
    │ │ ├── service
    │ │ ├── persistence
    │ │ │ ├── dao
    │ │ │ ├── entity
    │ │ │ └── repository
    └── infra
```
- 기본적인 Domain 기반의 package 구조로 구성합니다.
- source_root : 하위에 크게 common, domain, infra 영역으로 구분합니다.
  - common
    - Application 에서 공통으로 사용하는 class들의 패키지입니다.
  - domain : domain 관련 class들의 패키지입니다. 해당 패키지 하위에 서브 도메인으로 세분화 됩니다.
    - 서브 도메인
      - controller
        - 사용자의 요청을 받고 처리하는 역할을 담당합니다.
        - 오로지 요청, 데이터 검증, 응답만을 담당합니다.
      - service
        - 비즈니스 로직을 담당하는 패키지입니다.
        - 프로젝트 볼륨이 크지 않아 따로 Command, Query등으로 구분하지 않고 하나의 Service만 사용합니다.
      - implement
        - 비즈니스 로직을 이루기 위해 도구로서 상세 구현 로직을 갖고 있는 클래스들이 있습니다.
        - service에서 오로지 비즈니스로직을 처리하는 경우 코드 가독성 및 유지보수의 어려움이 있어 해당 Layer를 별도로 추가하였습니다.
        - 현재는 Project의 볼륨이 크지않아 해당 영역은 Optional이지만, 프로젝트가 커지는 경우 Required로 변경하여 비즈니스 로직을 상세 구현 로직으로 분리합니다.
      - persistence
        - 데이터를 영구적으로 저장하고 관리하는 데 사용되는 패키지입니다.
        - Repository, DAO로 나뉘어서 관리합니다.
      - dto
        - 흔히 "데이터 전송 객체"라는 개념으로 사용하지만, 해당 프로젝트에서는 그뿐만이 아니라 "1회성으로 사용하는 모든 객체"를 DTO라는 개념으로 사용하고 있습니다.
        - 최대한 immutable하게 사용합니다. (일부 Spring Framework에서 필수적으로 NoArgs, Getter, Setter가 필요한 클래스들은 예외로 간주합니다.)
  - infra : http, object storage 기타 등 인프라 전반 통신에 필요한 클래스들이 있습니다.

## Package Convention
- 해당 프로젝트는 좋은 Application 구조와 유지보수성을 위해 package(또는 Layer)간의 무분별한 참조를 지양하고 있습니다.
- layer
  - domain -> infra 접근 금지
  - domain -> common 접근 금지
  - common -> infra 접근 금지
- domain package
  - controller -> service -> implement(Optional) -> persistence(DAO -> repository)순으로 참조하며 역방향 참조를 금지합니다.
  - 같은 level의 참조고 금지합니다. 
    - ex) service -> service
    - implement layer에서는 일부 예외를 허용합니다.
  - entity class는 외부로 노출을 금지하며 DTO로 변환하여 리턴합니다.
  - 각 서브 도메인 별로 참조를 금지합니다.
    - 서브 도메인은 언제든지 별도 서비스 또는 모듈등으로 분리가 될 수 있기때문에 확장성을 고려하여 참조를 금지합니다.
    - 서로 통신해야 하는 경우가 생기면 eventually하게 호출하여 최대한 커플링을 낮추는걸 권장합니다.
  - Repository를 단독 접근하지 않고, DAO를 통해 접근합니다.
    - Repository를 구현부에서 직접 사용한 경우 일부 반복적인 코드가 들어 갈 수 있으며, 무분별한 참조를 막기 위해 해당 컨벤션을 지정합니다.
- ArchUnit으로 일부 강제화 되어있습니다.
- 테스트 케이스 영역은 간편한 작성을 위해 위 컨벤션에서 제외됩니다.

## Commit Message Convention
- 커밋 메시지를 기본적으로 아래와 같이 구성합니다.
```
CommitType : subject
(한 줄을 띄워 분리)
contents
```
| Commit Type | Description                        |
|-------------|------------------------------------|
| feat        | 새로운 기능 추가                          | 
| refactoring | 일반적으로 코드를 수정하는 경우 또는 리팩터링 하는 경우    | 
| fix         | 버그 및 오류 수정                         | 
| test        | 테스트 코드를 수정 또는 리팩터링 하는 경우           | 
| style       | 코드(로직)의 변경없이 코드 포맷, 컨밴션 등을 수정하는 경우 |
| chore       | 코드의 수정 없이 설정 등을 변경하는 경우            |
| docs        | 문서를 수정하는 경우                        |
| rename      | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우       |
| remove      | 파일을 삭제하는 작업만 수행한 경우                |
- Contents의 경우 subject로만 설명이 가능한 경우 생략이 가능합니다.
- 별도 이슈 티켓을 관리 안하므로 Commit은 유의미한 단위로 하되 개발자의 판단에 맡깁니다.

## 향후 과제
- 네이버 블로그 조회까지 실패할 경우 지금처럼 실패를 내려주는것은 UX 측면에서 많이 아쉽습니다. 블로그 데이터를 캐싱하여 네이버까지 실패 할 경우 캐싱된 데이터를 내려주면 더 좋을 것 같습니다.
- 로그 테이블(TRENDING_LOG)에 직접 쿼리를 수행하고 있는데, 해당 테이블의 성격상 데이터가 대용량으로 될 가능성이 높기 때문에 따로 aggregate하고, 인기 검색어도 뽑아내는 형식으로 하면 더 좋을 것 같습니다.
  - 단, 이럴 경우 완전 실시간으로 인기 검색어를 보여주기는 힘들것으로 보이나, 대용량 데이터의 경우 실시간으로 하는 이점보다 별도로 aggregate & 데이터 extract하여 시스템 안정성을 높이는 것이 더 높아보입니다.