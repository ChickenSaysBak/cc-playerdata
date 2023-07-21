package me.chickensaysbak.ccplayerdata.repository;

import me.chickensaysbak.ccplayerdata.model.Playerdata;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerdataRepository extends ListCrudRepository<Playerdata, UUID> {

    List<Playerdata> findAll(Pageable pageable);
    List<Playerdata> findAllByOrderByFirstPlayed(Pageable pageable);
    List<Playerdata> findAllByOrderByFirstPlayedDesc(Pageable pageable);
    Optional<Playerdata> findByUuid(UUID uuid);
    List<Playerdata> findByUsername(String username, Pageable pageable);
    List<Playerdata> findAllByUsernameContainingIgnoreCase(String keyword, Pageable pageable);
    List<Playerdata> findAllByOwnerIsNotNull(Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank, owner)
            FROM Playerdata
            WHERE firstPlayed >= :start AND firstPlayed < :end
            ORDER BY firstPlayed
            """)
    List<Playerdata> findAllBetweenTimes(Long start, Long end, Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank, owner)
            FROM Playerdata
            ORDER BY lastPlayed - firstPlayed DESC
            """)
    List<Playerdata> findAllByMostTime(Pageable pageable);

    @Query(value = """
            SELECT p.uuid, p.username,
              (SELECT MIN(first_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid) as first_played,
              (SELECT MAX(last_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid) as last_played,
              p.rank, p.owner
            FROM playerdata p
            WHERE p.owner IS NULL
            ORDER BY
              (SELECT MAX(last_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid) -
              (SELECT MIN(first_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid) DESC
            """, nativeQuery = true)
    List<Playerdata> findAllByMostTimeAltsCombined(Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank, owner)
            FROM Playerdata
            ORDER BY lastPlayed - firstPlayed
            """)
    List<Playerdata> findAllByLeastTime(Pageable pageable);

    @Query(value = """
            SELECT p.uuid, p.username,
              (SELECT MIN(first_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid) as first_played,
              (SELECT MAX(last_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid) as last_played,
              p.rank, p.owner
            FROM playerdata p
            WHERE p.owner IS NULL
            ORDER BY
              (SELECT MAX(last_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid) -
              (SELECT MIN(first_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid)
            """, nativeQuery = true)
    List<Playerdata> findAllByLeastTimeAltsCombined(Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank, owner)
            FROM Playerdata
            WHERE rank > 0
            """)
    List<Playerdata> findAllRanked(Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank, owner)
            FROM Playerdata
            WHERE rank = :rank
            """)
    List<Playerdata> findAllByRank(Integer rank, Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank, owner)
            FROM Playerdata
            WHERE firstPlayed < (SELECT lastPlayed FROM Playerdata WHERE uuid = :uuid)
              AND lastPlayed > (SELECT firstPlayed FROM Playerdata WHERE uuid = :uuid)
            ORDER BY
              ABS(firstPlayed - (SELECT firstPlayed FROM Playerdata WHERE uuid = :uuid))
              + ABS(lastPlayed - (SELECT lastPlayed FROM Playerdata WHERE uuid = :uuid))
            """)
    List<Playerdata> findAllByOverlap(UUID uuid, Pageable pageable);

    @Query(value = """
            SELECT p.uuid, p.username,
              (SELECT MIN(first_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid) as first_played,
              (SELECT MAX(last_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid) as last_played,
              p.rank, p.owner
            FROM playerdata p
            WHERE p.owner IS NULL
              AND first_played < (SELECT MAX(last_played) FROM playerdata p2 WHERE p2.owner = :uuid OR p2.uuid = :uuid)
              AND last_played > (SELECT MIN(first_played) FROM playerdata p2 WHERE p2.owner = :uuid OR p2.uuid = :uuid)
            ORDER BY
              ABS(
                (SELECT MIN(first_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid)
            	- (SELECT MIN(first_played) FROM playerdata p2 WHERE p2.owner = :uuid OR p2.uuid = :uuid)
              )
              + ABS(
                (SELECT MAX(last_played) FROM playerdata p2 WHERE p2.owner = p.uuid OR p2.uuid = p.uuid)
            	- (SELECT MAX(last_played) FROM playerdata p2 WHERE p2.owner = :uuid OR p2.uuid = :uuid)
              )
            """, nativeQuery = true)
    List<Playerdata> findAllByOverlapAltsCombined(UUID uuid, Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank, owner)
            FROM Playerdata
            WHERE owner = :uuid
            """)
    List<Playerdata> findAlts(UUID uuid, Pageable pageable);

    @Query("""
            SELECT new Playerdata(uuid, username, firstPlayed, lastPlayed, rank, owner)
            FROM Playerdata
            WHERE uuid IN (SELECT DISTINCT owner FROM Playerdata)
            """)
    List<Playerdata> findAltOwners(Pageable pageable);

}
