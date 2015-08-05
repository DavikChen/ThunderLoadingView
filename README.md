# ThunderLoadingView

#高仿易讯客户端loading效果
![效果](https://github.com/Rowandjj/ThunderLoadingView/blob/master/art/thunder_view_gif.gif)


#useage

- **xml**

```
 <com.taobao.library.ThunderLoadingView
            android:id="@+id/view"
            app:thunder_size="MEDIUM"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
```

```
 //create from xml
  ThunderLoadingView view = (ThunderLoadingView) findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click..", Toast.LENGTH_SHORT).show();
            }
        });
```
- **code**
```
      //create in code
       ThunderLoadingView thunderView = new ThunderLoadingView(this);
       thunderView.setSize(ThunderLoadingView.Size.MEDIUM);
       rootView.addView(thunderView);
```
