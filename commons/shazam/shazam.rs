/*
 * @Author: joword
 * @Date: 2024-08-11 12:02:33
 * @LastEditors: joword 23089538@qq.com
 * @LastEditTime: 2024-08-11 22:18:15
 * @FilePath: \demo-rust\commons\shazam\shazam.rs
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
pub struct Match{
    song_id: u32,
    song_tile: &str,
    author: &str,
    youtube_id: &str,
    time_stamp: u32,
    score: f64,
}

impl Match{
    pub fn find_matches(&self) -> Self {
        Self{}
    }

    pub fn get_relative_timing() -> map {
        
    }
}
