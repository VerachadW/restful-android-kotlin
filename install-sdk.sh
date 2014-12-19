#!/bin/bash

# Fix the CircleCI path
export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools:$PATH"

DEPS="$ANDROID_HOME/installed"

if [ ! -e $DEPS ]; then
    cp -r /usr/local/android-sdk-linux $ANDROID_HOME &&
    #Install SDK Components
    echo y | android update sdk -u -a -t tools &&
    echo y | android update sdk -u -a -t platform-tools &&
    echo y | android update sdk -u -a -t build-tools-21.1.2 &&
    echo y | android update sdk -u -a -t sys-img-armeabi-v7a-android-21 &&
    echo y | android update sdk -u -a -t sys-img-x86_64-android-21 &&
    echo y | android update sdk -u -a -t sys-img-x86-android-19 &&
    echo y | android update sdk -u -a -t android-21 &&
    echo y | android update sdk -u -a -t android-19 &&
    echo y | android update sdk -u -a -t addon-google_apis-google-21 &&
    echo y | android update sdk -u -a -t addon-google_apis-google-19 &&
    echo y | android update sdk -u -a -t addon-google_apis_x86-google-19 &&
    echo y | android update sdk -u -a -t extra-android-m2repository &&
    echo y | android update sdk -u -a -t extra-android-support &&
    echo y | android update sdk -u -a -t extra-google-m2repository &&
    echo y | android update sdk -u -a -t extra-google-google_play_services &&
    echo y | android update sdk -u -a -t extra-intel-Hardware_Accelerated_Execution_Manager &&
    #Create AVD for Unit Testing
    echo n | android create avd -n avd-test-19 -f -t android-19 -b default/x86 &&
    touch $DEPS
fi