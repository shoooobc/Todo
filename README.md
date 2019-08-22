# 簡易版ToDoリスト

## 使用した技術要素
* Thymeleaf
* Spring Data JPA
* MySQL Driver

## 全体の設計・構成
### トップページ
#### 新しくTodoを作成することができる
* Todo名を入れるテキストボックスが表示
  * Todo名を入れないとエラーが表示
  * 同じTodo名を登録しようとするとエラーが表示
* 期限を入れるボックスが表示
  * 期限を入れないとエラーが表示
  * 今日より前の日にちを入れるとエラーが表示
* Todoの追加ボタンが表示
#### 登録されているTodoが新しい順で表示されている
* 編集ボタンが表示されている
* 完了/未完了ボタンの表示（それぞれ色が変わる）
  
### 検索画面
#### Todoを検索することができる
* 検索ボックスが表示されている
  * あいあまい検索をすることができる

#### 検索結果のTodoが新しい順で表示される
* 編集ボタンが表示されている
* 未完了ボタンの表示
  * 完了されていると表示されなくなる（未完了だけ表示）

### 編集画面
#### Todoを変更することができる
* 変更前のTodo名が表示されている
* 変更前の期限が表示されている
* Todoを更新ボタンが表示

### 共通ヘッダー
#### ヘッダーの部分を共通化

## 開発環境のセットアップ手順

### Homebrewをインストールする
```
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```
### MySQLをインストールする
```
brew install mysql@5.7
```
### mavenをインストールする
```
brew install maven
```
### gradleをインストールする
```
brew install grable
```
### MySQLの環境変数設定
```
//ターミナル内
vim .bash_profile

//vimで表示したテキスト内
export PATH="/usr/local/opt/mysql@5.7/bin:$PATH"
```

### application.profileの設定
```
spring.datasource.url=jdbc:mysql://localhost:3306/Todo
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.database=MYSQL
spring.jpaq.hibernate.ddl-auto=update
```



## その他
### うまくいってないところ
* 編集画面でのエラー処理

### よくわからなかったところ
* 同じ値が入ったときのエラー処理
* 例外が発生したときのメッセージの表示
* 同じメソッドを使い回ししたときの処理（例：トップ画面からの編集と、検索からの編集での処理が終わったとの画面遷移）
