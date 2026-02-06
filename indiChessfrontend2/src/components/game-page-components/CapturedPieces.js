import React from "react";
import "../component-styles/CapturedPieces.css";

const CapturedPieces = ({ capturedWhite, capturedBlack }) => {
    const pieceSymbols = {
        'p': '♟', 'n': '♞', 'b': '♝', 'r': '♜', 'q': '♛',
        'P': '♙', 'N': '♘', 'B': '♗', 'R': '♖', 'Q': '♕'
    };

    return (
        <div className="captured-pieces">
            <div className="captured-section">
                <h4>Captured by White:</h4>
                <div className="pieces-row">
                    {capturedBlack.length === 0 ? (
                        <span className="no-pieces">None</span>
                    ) : (
                        capturedBlack.map((piece, index) => (
                            <span key={index} className="captured-piece">
                                {pieceSymbols[piece]}
                            </span>
                        ))
                    )}
                </div>
            </div>

            <div className="captured-section">
                <h4>Captured by Black:</h4>
                <div className="pieces-row">
                    {capturedWhite.length === 0 ? (
                        <span className="no-pieces">None</span>
                    ) : (
                        capturedWhite.map((piece, index) => (
                            <span key={index} className="captured-piece">
                                {pieceSymbols[piece]}
                            </span>
                        ))
                    )}
                </div>
            </div>
        </div>
    );
};

export default CapturedPieces;
