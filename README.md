# 블로그 검색 Project

## Project Description
- 해당 프로젝트 Open API를 활용한 블로그 검색 프로젝트입니다.
- 별도 첨부된 요구사항에 맞춰 블로그 검색 프로그램을 작성합니다.

## Technologies
- Java 17
- SpringBoot 3.1.1
- JPA
- Lombok
- ArchUnit
- H2

## Code Convention
- 기본적으로 [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) 를 준수합니다.
- 프로젝트 root에 있는 [intellij-java-google-style.xml](./intellij-java-google-style.xml)를 import하여 사용합니다.

## Package Structure
TBD

## Package Convention
- 해당 프로젝트는 좋은(?) Application 구조와 유지보수성을 위해 package(또는 Layer)간의 무분별한 참조를 지양하고 있습니다.
- layer
  - domain -> infra 접근 금지
  - domain -> common 접근 금지
  - common -> infra 접근 금지
- domain package
  - controller -> service -> persistence(DAO -> repository)순으로 참조하며 역방향 참조를 금지합니다.
  - 같은 level의 참조고 금지합니다. ex) service -> service
  - entity class는 외부로 노출을 금지하며 DTO로 변환하여 리턴합니다.
  - 각 layer별 Annotation을 강제화 합니다.
- ArchUnit으로 일부 강제화 되어있습니다.

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
| refactor    | 일반적으로 코드를 수정하는 경우 또는 리팩터링 하는 경우    | 
| fix         | 버그 및 오류 수정                         | 
| test        | 테스트 코드를 수정 또는 리팩터링 하는 경우           | 
| style       | 코드(로직)의 변경없이 코드 포맷, 컨밴션 등을 수정하는 경우 |
| chore       | 코드의 수정 없이 설정 등을 변경하는 경우            |
| docs        | 문서를 수정하는 경우                        |
| rename      | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우       |
| remove      | 파일을 삭제하는 작업만 수행한 경우                |
- Contents의 경우 subject로만 설명이 가능한 경우 생략이 가능합니다.
- 별도 이슈 티켓을 관리 안하므로 Commit은 유의미한 단위로 하되 개발자의 판단에 맡깁니다.

## Git Branch Strategy
- N/A

## Installation and Run
- TBD

## Application Profile
- TBD

## CI/CD Principle
- N/A