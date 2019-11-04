# HomeCheckListApp

2019年度の久留米高専の文化祭にて販売した技術書の第4章の内容のリポジトリです。

〜追記〜

`CheckListRepository`と`CheckListUseCas`eを可読性の観点から`CheckRepository`と`CheckUseCase`に変更しました。


誤植、誤ったコードについて

ViewModelのインスタンスはonActivityCreatedで作るようにしてください。
一部のコード、本内での説明でonAttachでインスタンスを作っていますが、それは間違えです。


申し訳ありません。