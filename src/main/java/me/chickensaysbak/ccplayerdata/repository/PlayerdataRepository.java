package me.chickensaysbak.ccplayerdata.repository;

import me.chickensaysbak.ccplayerdata.model.Playerdata;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerdataRepository extends ListCrudRepository<Playerdata, UUID> {

    default List<Playerdata> findAll(Pageable pageable) {
        if (pageable == null) return findAll();
        else return findAll(pageable);
    }

    List<Playerdata> findAllByOrderByFirstPlayed(Pageable pageable);
    List<Playerdata> findAllByOrderByFirstPlayedDesc(Pageable pageable);
    Optional<Playerdata> findByUuid(UUID uuid);
    List<Playerdata> findByUsername(String username, Pageable pageable);
    List<Playerdata> findAllByUsernameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank)
            FROM Playerdata
            WHERE firstPlayed >= :start AND firstPlayed < :end
            ORDER BY firstPlayed
            """)
    List<Playerdata> findAllBetweenTimes(Long start, Long end, Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank)
            FROM Playerdata
            ORDER BY lastPlayed - firstPlayed DESC
            """)
    List<Playerdata> findAllByMostTime(Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank)
            FROM Playerdata
            ORDER BY lastPlayed - firstPlayed
            """)
    List<Playerdata> findAllByLeastTime(Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank)
            FROM Playerdata
            WHERE rank > 0
            """)
    List<Playerdata> findAllRanked(Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank)
            FROM Playerdata
            WHERE rank = :rank
            """)
    List<Playerdata> findAllByRank(Integer rank, Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank)
            FROM Playerdata
            WHERE firstPlayed < (SELECT lastPlayed FROM Playerdata WHERE uuid = :uuid)
              AND lastPlayed > (SELECT firstPlayed FROM Playerdata WHERE uuid = :uuid)
            ORDER BY (
              ABS(firstPlayed - (SELECT firstPlayed FROM Playerdata WHERE uuid = :uuid))
              + ABS(lastPlayed - (SELECT lastPlayed FROM Playerdata WHERE uuid = :uuid))
            )
            """)
    List<Playerdata> findAllByOverlap(UUID uuid, Pageable pageable);

}
