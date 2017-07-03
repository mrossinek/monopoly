/*
 * CardType
 *
 * Max Rossmannek
 */

package monopoly;


public enum CardType {

	GET_BA, GET_PL, PAY_FP, PAY_PL, PAY_HH, MOVE_Y, MOVE_N, FREEJC

};

/*
	GET_BA  get money from bank
	GET_PL  get money from other players
	PAY_FP  pay money into free parking
	PAY_PL  pay money to other players
	PAY_HH  pay money for houses and hotels (special case)
	MOVE_Y  move to field (via GO)
	MOVE_N  move to field (skip GO)
	FREEJC  get a get-out-of-jail card
*/
