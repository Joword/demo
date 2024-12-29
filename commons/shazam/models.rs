
pub struct Name {
    song_id: u32,
    anchor_time: u32,
}

pub struct RecodeData {
    audio: &str,
    duration: f64,
    channels: i32,
    sample_rate: i32,
    sample_size: i32,
}