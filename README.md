# HomeCheckListApp

2019年度の久留米高専の文化祭にて販売した技術書の第4章の内容のリポジトリです。
少し変更点とうがあるのでこのリポジトリのソースコードを読む前に以下に書いている追記を読んでからコソースコードを読むようにしてください。

## 〜追記〜

`CheckListRepository`と`CheckListUseCas`eを可読性の観点からこのリポジトリでは`CheckRepository`と`CheckUseCase`に変更しています。

本では説明していない拡張関数↓を使っています。気をつけてください！！
ExtUtil.ktの中にあります。
```
// viewModelScopeというViewModelの寿命と同じJobを立ち上げます。
// 非同期処理開始時にspinnerがtrueに非同期処理終了時にspinnerがfalseになります。
fun ViewModel.launchDataLoad(spinner: MutableLiveData<Boolean>, block: suspend () -> Unit): Job {
    return viewModelScope.launch {
        try {
            spinner.value = true
            block()
        } catch (error: Exception) {
            throw error
        } finally {
            spinner.value = false
        }
    }
}
```
このコードはGoogle（本家）で使われている拡張関数でもあるので実装方法と使い方を知っておくととても便利です。


## 誤植、誤ったコードについて

ViewModelのインスタンスはonActivityCreatedで作るようにしてください。
一部のコード、本内での説明でonAttachでインスタンスを作っていますが、それは間違えです。
正しい書き方は以下の通りです。
```
override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    // 代わりにsetupViewModel()がある場合があります。
    homeViewModel = ViewModelProviders.of(activity!!)[HomeViewModel::class.java]
}
```

申し訳ありません。
