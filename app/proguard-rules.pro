# Add project specific ProGuard rules here.
-dontwarn com.xuhao.android.libsocket. **
-keep class com.xuhao.android.socket.impl.abilities. ** {*;}
-keep class com.xuhao.android.socket.impl.exceptions. ** {*;}
-keep class com.xuhao.android.socket.impl.environmentalmanager {*;}
-keep class com.xuhao.android.socket.impl.blockconnectionmanager {*;}
-keep class com.xuhao.android.socket.impl.unblockconnectionmanager {*;}
-keep class com.xuhao.android.socket.impl.socketactionhandler {*;}
-keep class com.xuhao.android.socket.impl.pulsemanager {*;}
-keep class com.xuhao.android.socket.impl.managerholder {*;}
-keep class com.xuhao.android.socket.interfaces. ** {*;}
-keep class com.xuhao.android.socket.sdk. ** {*;}
#Enum classes cannot be confused
-keepclassmembers enum * {
  public static ** [] values ​​();
  public static ** valueof (java.lang.string);
}
-keep class com.xuhao.android.socket.sdk.oksocketoptions $* {
  *;
}
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
