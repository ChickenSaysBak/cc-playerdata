package me.chickensaysbak.ccplayerdata.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Playerdata {

        @Id
        private UUID uuid;
        private String username;
        @Column(name = "first_played")
        private Long firstPlayed;
        @Column(name = "last_played")
        private Long lastPlayed;
        private int rank;

        public Playerdata() {}

        public Playerdata(UUID uuid, String username, Long firstPlayed, Long lastPlayed, int rank) {
                this.uuid = uuid;
                this.username = username;
                this.firstPlayed = firstPlayed;
                this.lastPlayed = lastPlayed;
                this.rank = rank;
        }

        public void setUuid(UUID uuid) {this.uuid = uuid;}
        public void setUsername(String username) {this.username = username;}
        public void setFirstPlayed(Long firstPlayed) {this.firstPlayed = firstPlayed;}
        public void setLastPlayed(Long lastPlayed) {this.lastPlayed = lastPlayed;}
        public void setRank(int rank) {this.rank = rank;}

        public UUID getUuid() {return uuid;}
        public String getUsername() {return username;}
        public Long getFirstPlayed() {return firstPlayed;}
        public Long getLastPlayed() {return lastPlayed;}
        public int getRank() {return rank;}

}
