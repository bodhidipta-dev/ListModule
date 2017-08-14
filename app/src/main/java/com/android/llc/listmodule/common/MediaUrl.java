/*
 * Copyright (c) 2017 Nam Nguyen, nam@ene.im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.llc.listmodule.common;

import android.net.Uri;

/**
 * @author eneim | 6/6/17.
 */

public enum MediaUrl {
  TEARS_OF_STEEL("http://www.sample-videos.com/video/mp4/480/big_buck_bunny_480p_5mb.mp4", 2.4f), //
  BIG_BUCK_BUNNY("http://www.sample-videos.com/video/mp4/480/big_buck_bunny_480p_5mb.mp4", 1.7777777778f),  //
  COSMOS_LAUNDROMATY("http://www.sample-videos.com/video/mp4/480/big_buck_bunny_480p_5mb.mp4", 2.4f),  //
  ;

  private final String url;
  private final float ratio;

  MediaUrl(String url, float ratio) {
    this.url = url;
    this.ratio = ratio;
  }

  MediaUrl(String url) {
    this(url, 1.7777777778f);
  }

  public String getUrl() {
    return url;
  }

  public Uri getUri() {
    return Uri.parse(this.url);
  }

  public float getRatio() {
    return ratio;
  }

  @Override public String toString() {
    return name() + "{" + "url='" + url + '\'' + ", ratio=" + ratio + '}';
  }
}
